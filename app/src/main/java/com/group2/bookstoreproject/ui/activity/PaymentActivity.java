package com.group2.bookstoreproject.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.codec.binary.Hex;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.codec.digest.DigestUtils;
import com.group2.bookstoreproject.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class PaymentActivity extends AppCompatActivity {

    private static final String VNPAY_URL = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    private static final String MERCHANT_ID = "7NHFIZA3";
    private static final String ACCESS_CODE = "YourAccessCode";
    private static final String SECRET_KEY = "QKJ5S206Z41HGUG7LU742WL0M1T8I3RO";
    private static final String RETURN_URL = "http://your-return-url.com";

    private TextView edtAmount, btnPay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_payment);

        edtAmount = findViewById(R.id.total_price);
        btnPay = findViewById(R.id.tv_Checkout);

        btnPay.setOnClickListener(v -> {
            String amount = edtAmount.getText().toString().trim();
            if (!amount.isEmpty()) {
                startPayment(amount);
            }
        });
    }

    private void startPayment(String amount) {
        String orderId = generateOrderId();
        String tmnCode = MERCHANT_ID;

        Map<String, String> params = new HashMap<>();
        params.put("vnp_Version", "2.1.0");
        params.put("vnp_Command", "pay");
        params.put("vnp_TmnCode", tmnCode);
        params.put("vnp_Amount", String.valueOf(Integer.parseInt(amount) * 100));
        params.put("vnp_CurrCode", "VND");
        params.put("vnp_TxnRef", orderId);
        params.put("vnp_OrderInfo", "Thanh toan don hang: " + orderId);
        params.put("vnp_OrderType", "other");
        params.put("vnp_Locale", "vn");
        params.put("vnp_ReturnUrl", RETURN_URL);
        params.put("vnp_IpAddr", "127.0.0.1");

        String vnp_CreateDate = getCurrentDateTime();
        params.put("vnp_CreateDate", vnp_CreateDate);

        StringBuilder hashData = new StringBuilder();
        StringBuilder queryString = new StringBuilder();
        for (Map.Entry<String, String> param : params.entrySet()) {
            if (hashData.length() > 0) {
                hashData.append('&');
                queryString.append('&');
            }
            hashData.append(param.getKey()).append('=').append(param.getValue());
            try {
                queryString.append(param.getKey()).append('=').append(URLEncoder.encode(param.getValue(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        String secureHash = new String(Hex.encodeHex(DigestUtils.sha256(hashData.toString() + SECRET_KEY)));
        queryString.append("&vnp_SecureHashType=SHA256&vnp_SecureHash=").append(secureHash);

        String paymentUrl = VNPAY_URL + "?" + queryString.toString();
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(paymentUrl));
        startActivity(browserIntent);
    }

    private String generateOrderId() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(new Date());
    }

    private String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(new Date());
    }
}
