package com.group2.bookstoreproject.data.model;

public class Discount {
    private String discountCode;
    private float percentage;
    private long expiryDate;

    public Discount() {
    }

    public Discount(String discountCode, float percentage, long expiryDate) {
        this.discountCode = discountCode;
        this.percentage = percentage;
        this.expiryDate = expiryDate;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public long getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(long expiryDate) {
        this.expiryDate = expiryDate;
    }
}
