package com.group2.bookstoreproject.data.model;

import java.util.List;

public class Order {
    private String orderId;
    private String userId;
    private double totalAmount;
    private int orderStatus;
    private long orderAt;
    private String transactionNo;
    private String address;
    private String discountId;
    private List<OrderItem> orderItems;

    public Order() {
    }

    public Order(String orderId, String userId, double totalAmount, int orderStatus, long orderAt, String transactionNo, String address, String discountId, List<OrderItem> orderItems) {
        this.orderId = orderId;
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
        this.orderAt = orderAt;
        this.transactionNo = transactionNo;
        this.address = address;
        this.discountId = discountId;
        this.orderItems = orderItems;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public long getOrderAt() {
        return orderAt;
    }

    public void setOrderAt(long orderAt) {
        this.orderAt = orderAt;
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDiscountId() {
        return discountId;
    }

    public void setDiscountId(String discountId) {
        this.discountId = discountId;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
