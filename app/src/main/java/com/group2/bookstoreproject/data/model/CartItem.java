package com.group2.bookstoreproject.data.model;

import java.util.Objects;

public class CartItem {
    private String cartItemId;
    private String accountId;
    private String bookId;
    private int quantity;
    private double price;

    public CartItem() {
    }

    public CartItem(String cartItemId, String accountId, String bookId, int quantity, double price) {
        this.cartItemId = cartItemId;
        this.accountId = accountId;
        this.bookId = bookId;
        this.quantity = quantity;
        this.price = price;
    }

    public String getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(String cartItemId) {
        this.cartItemId = cartItemId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CartItem cartItem = (CartItem) obj;
        return cartItemId.equals(cartItem.cartItemId);
    }

    @Override
    public int hashCode() {
        return cartItemId.hashCode();
    }
}
