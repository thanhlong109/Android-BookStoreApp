package com.group2.bookstoreproject.ui.common.orderList;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.group2.bookstoreproject.R;
import com.group2.bookstoreproject.base.BaseFragment;
import com.group2.bookstoreproject.databinding.FragmentOrderListBinding;


public class OrderListFragment extends BaseFragment<FragmentOrderListBinding, OrderListViewModel> {


    @NonNull
    @Override
    protected FragmentOrderListBinding inflateBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, boolean attachToParent) {
        return FragmentOrderListBinding.inflate(inflater, container, attachToParent);
    }

    @NonNull
    @Override
    protected Class<OrderListViewModel> getViewModelClass() {
        return OrderListViewModel.class;
    }
}