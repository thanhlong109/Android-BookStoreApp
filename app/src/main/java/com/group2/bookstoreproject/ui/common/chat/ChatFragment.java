package com.group2.bookstoreproject.ui.common.chat;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;


import android.os.Bundle;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import com.group2.bookstoreproject.base.BaseFragment;
import com.group2.bookstoreproject.data.model.ChatRoom;
import com.group2.bookstoreproject.databinding.FragmentChatBinding;



public class ChatFragment extends BaseFragment<FragmentChatBinding,ChatViewModel> {

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
                viewModel.addMessageToChatRoom(message);
            }
        });
    }

    @Override
    protected void observeViewModel() {
        super.observeViewModel();
        viewModel.getCurrentChatRoom().observe(getViewLifecycleOwner(), new Observer<ChatRoom>() {
            @Override
            public void onChanged(ChatRoom chatRoom) {
                viewModel.removeListener();
                viewModel.listenToMessagesInChatRoom();
            }
        });
    }
}