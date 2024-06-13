package com.group2.bookstoreproject.data.model;

public class CartItem {
    private String cartItemId;
    private  String shoppingCartId;
    private String bookId;
    private int quantity;
    private double price;

    public CartItem() {
    }

    public CartItem(String cartItemId, String shoppingCartId, String bookId, int quantity, double price) {
        this.cartItemId = cartItemId;
        this.shoppingCartId = shoppingCartId;
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

    public String getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(String shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
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
}
