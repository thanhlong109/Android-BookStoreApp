package com.group2.bookstoreproject.data.model;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class ChatListItem implements Serializable {
    private  User partner;
    private  ChatRoom chatRoom;

    public ChatListItem(User partner, ChatRoom chatRoom) {
        this.partner = partner;
        this.chatRoom = chatRoom;
    }

    public ChatListItem() {
    }

    public User getPartner() {
        return partner;
    }

    public void setPartner(User partner) {
        this.partner = partner;
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ChatListItem that = (ChatListItem) obj;
        return partner == that.partner && chatRoom == that.chatRoom;
    }
}
