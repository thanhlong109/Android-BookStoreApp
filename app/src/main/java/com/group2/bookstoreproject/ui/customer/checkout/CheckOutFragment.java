package com.group2.bookstoreproject.ui.customer.checkout;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.group2.bookstoreproject.R;
import com.group2.bookstoreproject.base.BaseFragment;
import com.group2.bookstoreproject.databinding.FragmentCheckOutBinding;

public class CheckOutFragment extends BaseFragment<FragmentCheckOutBinding,CheckOutViewModel> {

    @NonNull
    @Override
    protected FragmentCheckOutBinding inflateBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, boolean attachToParent) {
        return FragmentCheckOutBinding.inflate(inflater,container,attachToParent);
    }

    @NonNull
    @Override
    protected Class<CheckOutViewModel> getViewModelClass() {
        return CheckOutViewModel.class;
    }
}