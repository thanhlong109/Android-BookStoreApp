package com.group2.bookstoreproject.ui.customer.payment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private float total_price = 0;
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
    }

    // PaymentFragment.java
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        cartItemAdapter = new CartItemPaymentAdapter();
        binding.recyclerViewCartItems.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewCartItems.setAdapter(cartItemAdapter);

        total_price = (float) args.getSerializable("total");

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
