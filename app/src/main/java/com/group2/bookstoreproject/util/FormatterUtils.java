package com.group2.bookstoreproject.util;

import java.text.NumberFormat;
import java.util.Locale;

public class FormatterUtils {
    public static String ToMoneyText(Double value) {
        if (value == null) {
            return "";
        }

        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return currencyFormatter.format(value);
    }
}
