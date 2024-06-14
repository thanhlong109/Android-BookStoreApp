package com.group2.bookstoreproject.ui.admin.dashboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.group2.bookstoreproject.R;
import com.group2.bookstoreproject.base.BaseFragment;
import com.group2.bookstoreproject.databinding.FragmentDashboardBinding;


public class DashboardFragment extends BaseFragment<FragmentDashboardBinding, DashboardViewModel> {


    @NonNull
    @Override
    protected FragmentDashboardBinding inflateBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, boolean attachToParent) {
        return FragmentDashboardBinding.inflate(inflater, container, attachToParent);
    }

    @NonNull
    @Override
    protected Class<DashboardViewModel> getViewModelClass() {
        return DashboardViewModel.class;
    }
}