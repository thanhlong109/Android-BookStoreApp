package com.group2.bookstoreproject.ui.customer.cart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.group2.bookstoreproject.R;
import com.group2.bookstoreproject.base.BaseFragment;
import com.group2.bookstoreproject.data.model.CartItem;
import com.group2.bookstoreproject.data.model.User;
import com.group2.bookstoreproject.databinding.FragmentCartBinding;
import com.group2.bookstoreproject.ui.customer.payment.PaymentFragment;
import com.group2.bookstoreproject.ui.customer.profile.UpdateProfileFragment;
import com.group2.bookstoreproject.util.session.SessionManager;

import java.util.List;
import java.util.Objects;


public class CartFragment extends BaseFragment<FragmentCartBinding, CartViewModel> {
    private CartItemAdapter cartItemAdapter;
    private String accountId;

    @NonNull
    @Override
    protected FragmentCartBinding inflateBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, boolean attachToParent) {
        return FragmentCartBinding.inflate(inflater, container, attachToParent);
    }

    @NonNull
    @Override
    protected Class<CartViewModel> getViewModelClass() {
        return CartViewModel.class;
    }

    @Override
    protected void observeViewModel() {
        super.observeViewModel();
        viewModel.getCartItems().observe(getViewLifecycleOwner(), new Observer<List<CartItem>>() {
            @Override
            public void onChanged(List<CartItem> cartItems) {
                if (cartItems == null ||cartItems.isEmpty()) {
                    showEmptyCartLayout();
                } else {
                    showCartItems(cartItems);
                    calculateAndShowSubtotal(Objects.requireNonNull(viewModel.getCartItems().getValue()));
                }
            }
        });
        viewModel.getCartChangedLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean cartChanged) {
                if (cartChanged) {
                    viewModel.loadCartItems(accountId);
                }
            }
        });
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpRecyclerView();
        if (accountId != null) {
            viewModel.loadCartItems(accountId);
        }
        binding.buttonProceedToCheckout.setOnClickListener(v -> navigateToPaymentFragment());
    }

    private void navigateToPaymentFragment() {
        double total = calculateTotal(); // Assuming you have a method to calculate the total
        Bundle args = new Bundle();
        args.putDouble("total", total);

//        NavController navController = NavHostFragment.findNavController(this);
//        navController.navigate(R.id.paymentFragment, args);

        navigateToPage(R.id.action_navigation_cus_cart_to_paymentFragment, args);
    }

    private void setUpRecyclerView() {
        RecyclerView recyclerView = binding.recyclerViewCartItems;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cartItemAdapter = new CartItemAdapter(viewModel);
        recyclerView.setAdapter(cartItemAdapter);
    }

    private void showEmptyCartLayout() {
        binding.recyclerViewCartItems.setVisibility(View.GONE);
        binding.mainCartContentLayout.setVisibility(View.GONE);
        binding.emptyCartLayout.getRoot().setVisibility(View.VISIBLE);
    }

    private void showCartItems(List<CartItem> cartItems) {
        binding.recyclerViewCartItems.setVisibility(View.VISIBLE);
        binding.mainCartContentLayout.setVisibility(View.VISIBLE);
        binding.emptyCartLayout.getRoot().setVisibility(View.GONE);
        cartItemAdapter.submitList(cartItems);
    }

    private void calculateAndShowSubtotal(List<CartItem> cartItems) {
        double subtotal = 0.0;
        for (CartItem item : cartItems) {
            subtotal += item.getPrice();
        }
        TextView textViewSubtotal = binding.textViewSubtotal;
        textViewSubtotal.setText(String.format("%.0fVND", subtotal));
        double shipFee = 30000;
        double total = subtotal + shipFee;

        PaymentFragment fragment = new PaymentFragment();
        Bundle args = new Bundle();
        args.putDouble("total", total);
        fragment.setArguments(args);
    }

    private double calculateTotal() {
        double subtotal = 0.0;
        List<CartItem> cartItems = Objects.requireNonNull(viewModel.getCartItems().getValue());
        for (CartItem item : cartItems) {
            subtotal += item.getPrice();
        }
        //double shipFee = 30000;
        return subtotal;
    }
}