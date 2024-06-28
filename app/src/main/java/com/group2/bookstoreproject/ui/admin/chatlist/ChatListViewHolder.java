package com.group2.bookstoreproject.ui.admin.chatlist;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.group2.bookstoreproject.base.BaseItemViewHolder;
import com.group2.bookstoreproject.data.model.ChatListItem;
import com.group2.bookstoreproject.data.model.ChatRoom;
import com.group2.bookstoreproject.data.model.User;
import com.group2.bookstoreproject.databinding.RowItemChatListBinding;

public class ChatListViewHolder extends BaseItemViewHolder<ChatListItem, RowItemChatListBinding> {

    public ChatListViewHolder(@NonNull RowItemChatListBinding binding) {
        super(binding);
    }

    @Override
    public void bind(ChatListItem item) {
        User partner = item.getPartner();
        ChatRoom chatRoom = item.getChatRoom();
        Glide.with(itemContext).load(partner.getAvatar()).into(binding.ivChatItemAvatar);
        binding.tvChatItemUserName.setText(partner.getUserName());
        binding.tvChatItemContent.setText(chatRoom.getLastMessage());
    }

}
