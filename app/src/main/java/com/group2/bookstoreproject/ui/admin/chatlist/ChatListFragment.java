package com.group2.bookstoreproject.ui.admin.chatlist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.group2.bookstoreproject.R;
import com.group2.bookstoreproject.base.BaseFragment;
import com.group2.bookstoreproject.data.model.ChatListItem;
import com.group2.bookstoreproject.data.model.ChatRoom;
import com.group2.bookstoreproject.data.model.User;
import com.group2.bookstoreproject.databinding.FragmentChatListBinding;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.List;


public class ChatListFragment extends BaseFragment<FragmentChatListBinding, ChatListViewModel> {

    private ChatListRecyclerViewAdapter chatListRecyclerViewAdapter;
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

    @Override
    protected void observeViewModel() {
        super.observeViewModel();
        viewModel.getChatRooms().observe(getViewLifecycleOwner(), new Observer<List<ChatListItem>>() {
            @Override
            public void onChanged(List<ChatListItem> chatListItems) {
                chatListRecyclerViewAdapter.submitList(chatListItems);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //chat list
        chatListRecyclerViewAdapter = new ChatListRecyclerViewAdapter();
        binding.rvChatList.setAdapter(chatListRecyclerViewAdapter);
        binding.rvChatList.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}