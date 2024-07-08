package com.group2.bookstoreproject.util;

public class EnumUtils {
    public static String GetOrderStatusString(int value){
        switch (value){
            case 1: return "Đang giao hàng";
            case 2: return "Đã hoàn thành";
            case 3: return "Đã hủy";
            default: return "Đang giao hàng";
        }
    }
}
