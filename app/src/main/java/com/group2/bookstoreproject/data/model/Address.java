package com.group2.bookstoreproject.data.model;

public class Address {
    private String AddressName;
    private String AddressLongitude ;
    private String AddressLatitude ;

    public Address() {
    }

    public Address(String addressName, String addressLongitude, String addressLatitude) {
        AddressName = addressName;
        AddressLongitude = addressLongitude;
        AddressLatitude = addressLatitude;
    }

    public String getAddressName() {
        return AddressName;
    }

    public void setAddressName(String addressName) {
        AddressName = addressName;
    }

    public String getAddressLongitude() {
        return AddressLongitude;
    }

    public void setAddressLongitude(String addressLongitude) {
        AddressLongitude = addressLongitude;
    }

    public String getAddressLatitude() {
        return AddressLatitude;
    }

    public void setAddressLatitude(String addressLatitude) {
        AddressLatitude = addressLatitude;
    }
}
