package com.group2.bookstoreproject.data.model;

import java.util.List;

public class ShoppingCart {
    private String userId; // is also cart id, each user have 1 cart

    private List<Book> books;
    private double totalPrice;

    public ShoppingCart() {
    }

    public ShoppingCart(String userId, List<Book> books, double totalPrice) {
        this.userId = userId;
        this.books = books;
        this.totalPrice = totalPrice;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
