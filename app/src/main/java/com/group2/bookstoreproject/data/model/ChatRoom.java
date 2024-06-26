package com.group2.bookstoreproject.data.model;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class ChatRoom implements Serializable {
    private String chatRoomId;
    private boolean chatSeen;
    private long lastActiveTime;
    private List<String> members;

    private String lastMessage;

    public ChatRoom() {
    }

    public ChatRoom(String chatRoomId, boolean chatSeen, long lastActiveTime, List<String> members) {
        this.chatRoomId = chatRoomId;
        this.chatSeen = chatSeen;
        this.lastActiveTime = lastActiveTime;
        this.members = members;
    }

    public ChatRoom(boolean chatSeen, long lastActiveTime, List<String> members) {
        this.chatRoomId = UUID.randomUUID().toString();
        this.chatSeen = chatSeen;
        this.lastActiveTime = lastActiveTime;
        this.members = members;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public boolean isChatSeen() {
        return chatSeen;
    }

    public void setChatSeen(boolean chatSeen) {
        this.chatSeen = chatSeen;
    }

    public long getLastActiveTime() {
        return lastActiveTime;
    }

    public void setLastActiveTime(long lastActiveTime) {
        this.lastActiveTime = lastActiveTime;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }
}
