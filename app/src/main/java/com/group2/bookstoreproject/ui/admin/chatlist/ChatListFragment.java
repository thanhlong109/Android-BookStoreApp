package com.group2.bookstoreproject.ui.admin.chatlist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.group2.bookstoreproject.R;
import com.group2.bookstoreproject.base.BaseFragment;
import com.group2.bookstoreproject.databinding.FragmentChatListBinding;


public class ChatListFragment extends BaseFragment<FragmentChatListBinding, ChatListViewModel> {


    @NonNull
    @Override
    protected FragmentChatListBinding inflateBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, boolean attachToParent) {
        return FragmentChatListBinding.inflate(inflater, container, attachToParent);
    }

    @NonNull
    @Override
    protected Class<ChatListViewModel> getViewModelClass() {
        return ChatListViewModel.class;
    }
}