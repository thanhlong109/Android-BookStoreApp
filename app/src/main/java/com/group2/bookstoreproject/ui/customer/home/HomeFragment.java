package com.group2.bookstoreproject.ui.customer.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.group2.bookstoreproject.base.BaseFragment;
import com.group2.bookstoreproject.databinding.FragmentCartBinding;
import com.group2.bookstoreproject.databinding.FragmentHomeBinding;


public class HomeFragment extends BaseFragment<FragmentHomeBinding,HomeViewHolder> {

    @NonNull
    @Override
    protected FragmentHomeBinding inflateBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, boolean attachToParent) {
        return FragmentHomeBinding.inflate(inflater, container,attachToParent);
    }

    @NonNull
    @Override
    protected Class<HomeViewHolder> getViewModelClass() {
        return HomeViewHolder.class;
    }
}