package com.group2.bookstoreproject.ui.common.chat;


import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.group2.bookstoreproject.base.BaseItemViewHolder;
import com.group2.bookstoreproject.base.customView.CustomChatMessage;
import com.group2.bookstoreproject.data.model.ChatMessage;
import com.group2.bookstoreproject.data.model.User;
import com.group2.bookstoreproject.databinding.RowItemChatMessageBinding;

public class ChatMessageViewHolder extends BaseItemViewHolder<ChatMessage, RowItemChatMessageBinding> {
    private User currentUser;
    private User receiverUser;
    private ChatMessageRecyclerViewAdapter.OnItemDisplayListener onItemDisplayListener;

    public ChatMessageViewHolder(@NonNull RowItemChatMessageBinding binding, User currentUser, User receiverUser, ChatMessageRecyclerViewAdapter.OnItemDisplayListener onItemDisplayListener) {
        super(binding);
        this.currentUser = currentUser;
        this.receiverUser = receiverUser;
        this.onItemDisplayListener = onItemDisplayListener;
    }

    @Override
    public void bind(ChatMessage item) {
        CustomChatMessage chatMessage = binding.chatMessage;
        chatMessage.refreshContent();
        chatMessage.setContent(item.getMessageContent());

        if(item.getSenderId().equals(currentUser.getUserId())){
            chatMessage.setStartDirection(false);
        }else{
            chatMessage.setStartDirection(true);
            Glide.with(itemContext).load(receiverUser.getAvatar()).into(binding.chatMessage.binding.ivMessageLeft);
        }
        chatMessage.setMessageSeen(item.isSeen());
        chatMessage.setContent(item.getMessageContent());

    }
}
