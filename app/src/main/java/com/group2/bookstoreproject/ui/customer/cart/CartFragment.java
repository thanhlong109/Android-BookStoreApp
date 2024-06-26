package com.group2.bookstoreproject.ui.customer.cart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.group2.bookstoreproject.base.BaseFragment;
import com.group2.bookstoreproject.data.model.CartItem;
import com.group2.bookstoreproject.databinding.FragmentCartBinding;

import java.util.List;


public class CartFragment extends BaseFragment<FragmentCartBinding, CartViewModel> {
    private CartItemAdapter cartItemAdapter;
    private String accountId;

    @NonNull
    @Override
    protected FragmentCartBinding inflateBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, boolean attachToParent) {
        return FragmentCartBinding.inflate(inflater, container,attachToParent);
    }

    @NonNull
    @Override
    protected Class<CartViewModel> getViewModelClass() {
        return CartViewModel.class;
    }

    @Override
    protected void observeViewModel() {
        super.observeViewModel();
        viewModel.getCartItems(accountId).observe(getViewLifecycleOwner(), new Observer<List<CartItem>>() {
            @Override
            public void onChanged(List<CartItem> cartItems) {
                cartItemAdapter.submitList(cartItems);
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountId = getArguments().getString("accountId");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = binding.recyclerViewCartItems;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cartItemAdapter = new CartItemAdapter(viewModel);
        recyclerView.setAdapter(cartItemAdapter);

        observeViewModel();
    }
}