package com.group2.bookstoreproject.data.model;

public class Notification {
    private String userId;
    private long sendAt; //timestamp also id
    private String message;
    private boolean isRead;
    private String type;
    private String action;

    public Notification() {
    }

    public Notification(String userId, long sendAt, String message, boolean isRead, String type, String action) {
        this.userId = userId;
        this.sendAt = sendAt;
        this.message = message;
        this.isRead = isRead;
        this.type = type;
        this.action = action;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getSendAt() {
        return sendAt;
    }

    public void setSendAt(long sendAt) {
        this.sendAt = sendAt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
