package com.group2.bookstoreproject.data.model;

public class OrderItem {
    private String orderItemId;
    private  String orderId;
    private String bookId;
    private int quantity;
    private double price;
    public OrderItem(){

    }

    public OrderItem(String orderItemId,String orderId, String bookId, int quantity, double price) {
        this.orderItemId = orderItemId;
        this.bookId = bookId;
        this.orderId = orderId;
        this.quantity = quantity;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
    }
}
