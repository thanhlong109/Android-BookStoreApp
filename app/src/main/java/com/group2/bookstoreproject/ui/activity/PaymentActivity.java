package com.group2.bookstoreproject.ui.activity;

import android.content.Intent;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.group2.bookstoreproject.R;
import com.group2.bookstoreproject.ui.customer.payment.Api.CreateOrder;
import com.group2.bookstoreproject.ui.customer.profile.ProfileFragment;

import org.json.JSONObject;


import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class PaymentActivity extends AppCompatActivity {

    private TextView edtAmount;
    private Button btnPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_payment);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // ZaloPay SDK Init
        ZaloPaySDK.init(2553, Environment.SANDBOX);

        edtAmount = findViewById(R.id.total_price);
        btnPay = findViewById(R.id.tv_Checkout);



        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateOrder orderApi = new CreateOrder();
                try {
                    JSONObject data = orderApi.createOrder(edtAmount.getText().toString());
                    Log.d("Amount", edtAmount.getText().toString());

                    String code = data.getString("return_code");
                    Toast.makeText(getApplicationContext(), "return_code: " + code, Toast.LENGTH_LONG).show();

                    if (code.equals("1")) {
                        String token = data.getString("zp_trans_token");
                        ZaloPaySDK.getInstance().payOrder(PaymentActivity.this, token, "demozpdk://app", new PayOrderListener() {
                            @Override
                            public void onPaymentSucceeded(String s, String s1, String s2) {
                                Intent intent = new Intent(PaymentActivity.this, ProfileFragment.class);
                                startActivity(intent);
                                Toast.makeText(PaymentActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onPaymentCanceled(String s, String s1) {
                                Intent intent = new Intent(PaymentActivity.this, ProfileFragment.class);
                                startActivity(intent);
                                Toast.makeText(PaymentActivity.this, "Canceled", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onPaymentError(ZaloPayError zaloPayError, String s, String s1) {
                                Intent intent = new Intent(PaymentActivity.this, ProfileFragment.class);
                                startActivity(intent);
                                Toast.makeText(PaymentActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }
}

