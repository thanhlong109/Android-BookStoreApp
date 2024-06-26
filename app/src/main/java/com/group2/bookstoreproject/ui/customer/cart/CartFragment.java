package com.group2.bookstoreproject.ui.customer.cart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.group2.bookstoreproject.base.BaseFragment;
import com.group2.bookstoreproject.data.model.Category;
import com.group2.bookstoreproject.databinding.FragmentCartBinding;


public class CartFragment extends BaseFragment<FragmentCartBinding, CartViewModel> {


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


}