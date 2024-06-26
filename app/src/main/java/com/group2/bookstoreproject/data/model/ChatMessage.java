package com.group2.bookstoreproject.data.model;

import android.util.Log;

import java.io.Serializable;
import java.util.Objects;

public class ChatMessage implements Serializable {
    private long sendTime;// also id
    private String chatRoomId;
    private String senderId;
    private String receiverId;
    private boolean seen;
    private String messageContent;

    public ChatMessage(long sendTime, String chatRoomId, String senderId, String receiverId, boolean seen, String messageContent) {
        this.sendTime = sendTime;
        this.chatRoomId = chatRoomId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.seen = seen;
        this.messageContent = messageContent;
    }

    public ChatMessage() {
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    public String getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    @Override
    public boolean equals(Object o) {
        
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatMessage that = (ChatMessage) o;
        return sendTime == that.sendTime &&
                seen == that.seen &&
                Objects.equals(chatRoomId, that.chatRoomId) &&
                Objects.equals(senderId, that.senderId) &&
                Objects.equals(receiverId, that.receiverId) &&
                Objects.equals(messageContent, that.messageContent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatRoomId, senderId, receiverId, messageContent, sendTime, seen);
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "chatRoomId='" + chatRoomId + '\'' +
                ", senderId='" + senderId + '\'' +
                ", receiverId='" + receiverId + '\'' +
                ", messageContent='" + messageContent + '\'' +
                ", sendTime=" + sendTime +
                ", isSeen=" + seen +
                '}';
    }
}
