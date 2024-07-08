package com.group2.bookstoreproject.ui.customer.payment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.group2.bookstoreproject.base.BaseFragment;
import com.group2.bookstoreproject.data.model.CartItem;
import com.group2.bookstoreproject.data.model.User;
import com.group2.bookstoreproject.databinding.FragmentPaymentBinding;
import com.group2.bookstoreproject.util.session.SessionManager;

import java.util.List;

import retrofit2.http.Tag;

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
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
    }


}
