package com.group2.bookstoreproject.ui.common.chat;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.group2.bookstoreproject.base.BaseAdapter;
import com.group2.bookstoreproject.data.model.ChatMessage;
import com.group2.bookstoreproject.data.model.User;
import com.group2.bookstoreproject.databinding.RowItemChatMessageBinding;

public class ChatMessageRecyclerViewAdapter extends BaseAdapter<ChatMessage,ChatMessageViewHolder> {
    private User currentUser;
    private User receiverUser;

    public ChatMessageRecyclerViewAdapter(User currentUser, User receiverUser) {
        this.currentUser = currentUser;
        this.receiverUser = receiverUser;
    }

    // khởi tạo binding cho ChatMessageViewHolder
    @Override
    protected ChatMessageViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent, int viewType) {
        return new ChatMessageViewHolder(RowItemChatMessageBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false), currentUser,receiverUser);
    }


    // dùng này để tối ưu list
    @Override
    protected DiffUtil.ItemCallback<ChatMessage> differCallBack() {
        return new DiffUtil.ItemCallback<ChatMessage>() {
            @Override
            public boolean areItemsTheSame(@NonNull ChatMessage oldItem, @NonNull ChatMessage newItem) {
                return oldItem.getSendTime() == newItem.getSendTime();
            }

            // cần định nghĩa phương thức equals trong Object ở context này là ChatMessage
            @Override
            public boolean areContentsTheSame(@NonNull ChatMessage oldItem, @NonNull ChatMessage newItem) {
                return oldItem.equals(newItem);
            }
        };
    }
}
