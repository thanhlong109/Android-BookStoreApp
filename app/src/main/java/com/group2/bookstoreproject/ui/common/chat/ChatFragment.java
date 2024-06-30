package com.group2.bookstoreproject.ui.common.chat;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.group2.bookstoreproject.base.BaseDialog;
import com.group2.bookstoreproject.base.BaseFragment;
import com.group2.bookstoreproject.data.model.ChatListItem;
import com.group2.bookstoreproject.data.model.ChatMessage;
import com.group2.bookstoreproject.data.model.ChatRoom;
import com.group2.bookstoreproject.data.model.User;
import com.group2.bookstoreproject.data.model.base.Resource;
import com.group2.bookstoreproject.databinding.DialogStartChatBinding;
import com.group2.bookstoreproject.databinding.FragmentChatBinding;

import java.util.List;



public class ChatFragment extends BaseFragment<FragmentChatBinding,ChatViewModel> {
    private  ChatMessageRecyclerViewAdapter chatMessageRecyclerViewAdapter;
    private Dialog startChatDialog;
    @NonNull
    @Override
    protected FragmentChatBinding inflateBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, boolean attachToParent) {
        return FragmentChatBinding.inflate(inflater, container,attachToParent);
    }

    @NonNull
    @Override
    protected Class<ChatViewModel> getViewModelClass() {
        return ChatViewModel.class;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle =  getArguments();
        if(bundle !=null && bundle.containsKey("ChatRoomData")){
            Log.d("test", "have data");
            ChatListItem chatListItem = (ChatListItem) bundle.getSerializable("ChatRoomData");
            viewModel.setReceiver(chatListItem.getPartner());
            viewModel.setCurrentChatRoom(chatListItem.getChatRoom());
        }
        binding.ibSend.setOnClickListener(v -> onSendMessage());
        setUpDialog();
        RecyclerView recyclerView = binding.rvChatMessages;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        chatMessageRecyclerViewAdapter = new ChatMessageRecyclerViewAdapter(viewModel.sender, viewModel.receiver, (message) -> {
           viewModel.setSeen(message);
        });
        recyclerView.setAdapter(chatMessageRecyclerViewAdapter);


    }

    private void setUpDialog () {
        startChatDialog = new BaseDialog<DialogStartChatBinding>(getContext()) {
            @Override
            protected DialogStartChatBinding getViewBinding(LayoutInflater inflater) {
                return DialogStartChatBinding.inflate(inflater);
            }
            @Override
            protected void setupView() {
                binding.btnStartChat.setOnClickListener(v -> {
                    viewModel.addChatRoom();
                    dismiss();
                });
            }
        };

    }

    private void onSendMessage(){
        String message = binding.etInput.getText().toString().trim();
        if(message.length()>0){
            viewModel.addMessageToChatRoom(message);
            binding.etInput.setText("");
        }
    }


    @Override//lắng nghe
    protected void observeViewModel() {
        super.observeViewModel();


        viewModel.getChatMessages().observe(getViewLifecycleOwner(), new Observer<List<ChatMessage>>() {
            @Override
            public void onChanged(List<ChatMessage> chatMessages) {
                Log.d("test",""+chatMessages.size());
                chatMessageRecyclerViewAdapter.submitList(chatMessages, true);
            }
        });

        //khi phòng chat thay đổi thì tiến hành xóa listen cũ và request lên firebase lắng nghe messgae trong phòng chat mới

            viewModel.getCurrentChatRoom().observe(getViewLifecycleOwner(), new Observer<Resource<ChatRoom>>() {
                @Override
                public void onChanged(Resource<ChatRoom> chatRoomResource) {
                    switch (chatRoomResource.getStatus()){
                        case SUCCESS:
                            viewModel.removeListener();
                            viewModel.listenToMessagesInChatRoom(chatRoomResource.getData().getChatRoomId());
                            break;
                        case ERROR:
                            startChatDialog.show();
                            break;
                    }
                }
            });

    }

    @Override // hủy listener cho nhẹ máy
    public void onDestroy() {
        super.onDestroy();
        viewModel.removeListener();
    }
}