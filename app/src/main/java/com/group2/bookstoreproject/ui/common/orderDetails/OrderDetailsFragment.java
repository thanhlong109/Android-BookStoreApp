package com.group2.bookstoreproject.ui.common.orderDetails;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.group2.bookstoreproject.R;
import com.group2.bookstoreproject.base.BaseFragment;
import com.group2.bookstoreproject.databinding.FragmentOrderDetailsBinding;


public class OrderDetailsFragment extends BaseFragment<FragmentOrderDetailsBinding, OrderDetailsViewModel> {


    @NonNull
    @Override
    protected FragmentOrderDetailsBinding inflateBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, boolean attachToParent) {
        return FragmentOrderDetailsBinding.inflate(inflater, container, attachToParent);
    }

    @NonNull
    @Override
    protected Class<OrderDetailsViewModel> getViewModelClass() {
        return OrderDetailsViewModel.class;
    }
}