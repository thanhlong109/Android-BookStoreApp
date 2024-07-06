package com.group2.bookstoreproject.data.model;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class User implements Serializable {
    private String userId;
    private String userName;
    private String password;
    private String email;
    private String fullName;
    private long dateOfBirth;
    private String phone;
    private String avatar;
    private int role;
    private long joinedAt;
    private int status;
    private String deviceToken;
    private Address address;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public User() {
    }

    public User(String userId, String userName, String password, String email, String fullName, long dateOfBirth, String phone, String avatar, int role, long joinedAt, int status, String deviceToken, Address address) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.avatar = avatar;
        this.role = role;
        this.joinedAt = joinedAt;
        this.status = status;
        this.deviceToken = deviceToken;
        this.address = address;
    }

    public User(String userId, String fullName, long dateOfBirth) {
        this.userId = userId;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User that = (User) obj;
        return userId == that.getUserId() && userName == that.userName && password == that.password && email == that.getEmail() && fullName == that.getFullName() &&
                dateOfBirth == that.getDateOfBirth() && phone == that.getPhone() && avatar == that.getAvatar() && role == that.getRole() && joinedAt == that.joinedAt
                && status == that.status && deviceToken == that.deviceToken;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", phone='" + phone + '\'' +
                ", avatar='" + avatar + '\'' +
                ", role=" + role +
                ", joinedAt=" + joinedAt +
                ", status=" + status +
                ", deviceToken='" + deviceToken + '\'' +
                '}';
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public long getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(long dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public long getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(long joinedAt) {
        this.joinedAt = joinedAt;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }
}
