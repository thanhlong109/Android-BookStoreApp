package com.group2.bookstoreproject.data.model;

public class Shipping {
    private long orderId;//also shipping id
    private String userId;
    private long startDay;
    private long completeDay;

    public Shipping(long orderId, String userId, long startDay, long completeDay) {
        this.orderId = orderId;
        this.userId = userId;
        this.startDay = startDay;
        this.completeDay = completeDay;
    }

    public Shipping() {
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getStartDay() {
        return startDay;
    }

    public void setStartDay(long startDay) {
        this.startDay = startDay;
    }

    public long getCompleteDay() {
        return completeDay;
    }

    public void setCompleteDay(long completeDay) {
        this.completeDay = completeDay;
    }
}
