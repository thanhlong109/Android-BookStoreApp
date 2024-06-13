package com.group2.bookstoreproject.data.model;

public class UserDiscount {
    private String discountCode;
    private String userId;
    private boolean used;

    public UserDiscount() {
    }

    public UserDiscount(String discountCode, String userId, boolean used) {
        this.discountCode = discountCode;
        this.userId = userId;
        this.used = used;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}
