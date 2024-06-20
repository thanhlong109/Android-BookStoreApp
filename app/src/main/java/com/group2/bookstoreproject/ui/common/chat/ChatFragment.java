package com.group2.bookstoreproject.ui.common.chat;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import com.group2.bookstoreproject.base.BaseFragment;
import com.group2.bookstoreproject.data.model.ChatMessage;
import com.group2.bookstoreproject.data.model.ChatRoom;
import com.group2.bookstoreproject.data.model.User;
import com.group2.bookstoreproject.databinding.FragmentChatBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class ChatFragment extends BaseFragment<FragmentChatBinding,ChatViewModel> {
    //private  ChatMessageRecyclerViewAdapter chatMessageRecyclerViewAdapter;
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
        binding.btnStartChat.setOnClickListener(v -> viewModel.addChatRoom("reciverId"));
        binding.ibSend.setOnClickListener(v -> {
            String message = binding.etInput.getText().toString().trim();
            if(message.length()>0){
                viewModel.addMessageToChatRoom(message, "1d62e7b5-1cfb-4977-8a10-4ac9d22364ac");
            }
        });
        User user = new User();
        user.setUserId("thisiSenderId");
        user.setAvatar("https://firebasestorage.googleapis.com/v0/b/bookstore-832c5.appspot.com/o/mi_quang.jpg?alt=media&token=c23a5a34-8048-4685-9bda-8df2943cb4fa");
        User receiver = new User();
        receiver.setUserId("reciverId");
        receiver.setAvatar("https://firebasestorage.googleapis.com/v0/b/bookstore-832c5.appspot.com/o/tiger.jpg?alt=media&token=630c1c03-e576-4bb6-84cc-85b24081e9db");
        List<ChatMessage> chatList = new ArrayList<>();
        for(int i = 1; i<10;i++){
            ChatMessage c = new ChatMessage();
            c.setSeen(false);
            c.setSendTime(System.currentTimeMillis()+i*2);
            c.setMessageContent("chat "+i);
            if(i%2==0){
                c.setSenderId(user.getUserId());
                c.setReceiverId(receiver.getUserId());
            }else{
                c.setSenderId(receiver.getUserId());
                c.setReceiverId(user.getUserId());
            }
            c.setChatRoomId("1d62e7b5-1cfb-4977-8a10-4ac9d22364ac");

            chatList.add(c);
        }

        RecyclerView recyclerView = binding.rvChatMessages;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ChatMessageRecyclerViewAdapter adapter = new ChatMessageRecyclerViewAdapter(user, receiver);
        recyclerView.setAdapter(adapter);
        adapter.submitList(chatList);
    }

    @Override
    protected void observeViewModel() {
        super.observeViewModel();
//        viewModel.getCurrentChatRoom().observe(getViewLifecycleOwner(), new Observer<ChatRoom>() {
//            @Override
//            public void onChanged(ChatRoom chatRoom) {
//                viewModel.removeListener();
//                viewModel.listenToMessagesInChatRoom();
//            }
//        });
    }
}