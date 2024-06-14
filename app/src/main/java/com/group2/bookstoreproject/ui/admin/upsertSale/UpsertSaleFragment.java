package com.group2.bookstoreproject.ui.admin.upsertSale;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.group2.bookstoreproject.R;
import com.group2.bookstoreproject.base.BaseFragment;
import com.group2.bookstoreproject.databinding.FragmentUpsertSaleBinding;


public class UpsertSaleFragment extends BaseFragment<FragmentUpsertSaleBinding, UpsertSaleViewModel> {


    @NonNull
    @Override
    protected FragmentUpsertSaleBinding inflateBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, boolean attachToParent) {
        return FragmentUpsertSaleBinding.inflate(inflater,container,attachToParent );
    }

    @NonNull
    @Override
    protected Class<UpsertSaleViewModel> getViewModelClass() {
        return UpsertSaleViewModel.class;
    }
}