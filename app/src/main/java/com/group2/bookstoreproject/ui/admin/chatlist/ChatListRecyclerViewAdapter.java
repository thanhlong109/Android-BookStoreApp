package com.group2.bookstoreproject.ui.admin.chatlist;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.group2.bookstoreproject.base.BaseAdapter;
import com.group2.bookstoreproject.data.model.ChatListItem;
import com.group2.bookstoreproject.databinding.RowItemChatListBinding;

public class ChatListRecyclerViewAdapter extends BaseAdapter<ChatListItem, ChatListViewHolder> {

    @Override
    protected ChatListViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent, int viewType) {
        return new ChatListViewHolder(RowItemChatListBinding.inflate(inflater,parent, false));
    }

    @Override
    protected DiffUtil.ItemCallback<ChatListItem> differCallBack() {
        return new DiffUtil.ItemCallback<ChatListItem>() {
            @Override
            public boolean areItemsTheSame(@NonNull ChatListItem oldItem, @NonNull ChatListItem newItem) {
                return oldItem.getChatRoom().getChatRoomId() == newItem.getChatRoom().getChatRoomId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull ChatListItem oldItem, @NonNull ChatListItem newItem) {
                return oldItem.equals(newItem) ;
            }
        };
    }
}
