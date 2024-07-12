package com.group2.bookstoreproject.ui.customer.payment;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.group2.bookstoreproject.R;
import com.group2.bookstoreproject.base.BaseFragment;
import com.group2.bookstoreproject.data.model.CartItem;
import com.group2.bookstoreproject.data.model.User;
import com.group2.bookstoreproject.databinding.FragmentPaymentBinding;
import com.group2.bookstoreproject.ui.activity.CustomerActivity;
import com.group2.bookstoreproject.ui.activity.PaymentResultActivity;
import com.group2.bookstoreproject.ui.customer.payment.Api.CreateOrder;
import com.group2.bookstoreproject.util.session.SessionManager;

import org.json.JSONObject;

import java.util.List;

import retrofit2.http.Tag;
import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class PaymentFragment extends BaseFragment<FragmentPaymentBinding, PaymentViewModel> {

    private CartItemPaymentAdapter cartItemAdapter;

    private String accountId;
    private double total_price = 0;
    @NonNull
    @Override
    protected FragmentPaymentBinding inflateBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, boolean attachToParent) {
        return FragmentPaymentBinding.inflate(inflater, container, attachToParent);
    }

    @NonNull
    @Override
    protected Class<PaymentViewModel> getViewModelClass() {
        return PaymentViewModel.class;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SessionManager sessionManager = SessionManager.getInstance();
        User loggedInUser = sessionManager.getLoggedInUser();
        if (loggedInUser != null) {
            accountId = loggedInUser.getUserId();
        }

        if (getArguments() != null) {
            total_price = getArguments().getDouble("total");
        } else {
            // Handle the case where arguments are not passed
            total_price = 0.0;
        }

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // ZaloPay SDK Init
        ZaloPaySDK.init(2553, Environment.SANDBOX);

        cartItemAdapter = new CartItemPaymentAdapter();
        binding.recyclerViewCartItems.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewCartItems.setAdapter(cartItemAdapter);

        // Display the total price
        binding.tvTotalPriceItems.setText(String.format("%.0fVND", total_price));
        binding.tvTotalDelivery.setText("30000VND");

        double total = total_price + 30000;
        TextView textViewTotal = binding.tvTotalPrice;
        textViewTotal.setText(String.format("%.0fVND", total));
        binding.totalPrice.setText(String.format("%.0fVND", total));
        viewModel.getCartItemsLiveData().observe(getViewLifecycleOwner(), new Observer<List<CartItem>>() {
            @Override
            public void onChanged(List<CartItem> cartItems) {
                cartItemAdapter.submitList(cartItems);
            }
        });

        viewModel.getUserLiveData().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user != null) {
                    //binding.tvFullName.setText(user.getFullName());
                    binding.tvEmailv.setText(user.getEmail());
                    binding.tvSDT.setText(user.getPhone());
                    if (user.getAddress() != null) {
                        binding.tvLocation.setText(user.getAddress().getAddressName());
                    }
                } else {
                    //Log.d(Tag, "User not found");
                }
            }
        });

        viewModel.loadCartItems(accountId);
        viewModel.loadUser(accountId);  // Load user data

        binding.btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomerActivity.setCallBackWhenResume(() -> {
                    Intent intent = new Intent(requireActivity(), PaymentResultActivity.class);
                    intent.putExtra("result", "Thanh Toán Thành Công");
                    //Log.d("result", "a");
                    startActivity(intent);
                });
                CreateOrder orderApi = new CreateOrder();
                try {
                    JSONObject data = orderApi.createOrder("70000");
                    Log.d("Amount", total+"");

                    String code = data.getString("return_code");
                    //Toast.makeText(getApplicationContext(), "return_code: " + code, Toast.LENGTH_LONG).show();
                    Log.d("code", code);
                    if (code.equals("1")) {
                        String token = data.getString("zp_trans_token");
                        ZaloPaySDK.getInstance().payOrder((CustomerActivity)requireActivity(), token, "demozpdk://app", new PayOrderListener() {
                            @Override
                            public void onPaymentSucceeded(String s, String s1, String s2) {

                                //Toast.makeText(PaymentFragment.this, "Success", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onPaymentCanceled(String s, String s1) {
                                Intent intent = new Intent(requireActivity(), PaymentResultActivity.class);
                                intent.putExtra("result", "Đã Hủy Thanh Toán");
                                startActivity(intent);
                                //Toast.makeText(PaymentActivity.this, "Canceled", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onPaymentError(ZaloPayError zaloPayError, String s, String s1) {
                                Intent intent = new Intent(requireActivity(), PaymentResultActivity.class);
                                intent.putExtra("result", "Lỗi Thanh Toán");
                                startActivity(intent);
                                //Toast.makeText(PaymentActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
