package com.group2.bookstoreproject.ui.common.chat;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import android.view.LayoutInflater;

import android.view.ViewGroup;

import com.group2.bookstoreproject.base.BaseFragment;
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
}