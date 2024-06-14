package com.group2.bookstoreproject.ui.admin.upsertBook;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.group2.bookstoreproject.R;
import com.group2.bookstoreproject.base.BaseFragment;
import com.group2.bookstoreproject.databinding.FragmentUpsertBookBinding;


public class UpsertBookFragment extends BaseFragment<FragmentUpsertBookBinding, UpsertViewModel> {

    @NonNull
    @Override
    protected FragmentUpsertBookBinding inflateBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, boolean attachToParent) {
        return FragmentUpsertBookBinding.inflate(inflater,container,attachToParent);
    }

    @NonNull
    @Override
    protected Class<UpsertViewModel> getViewModelClass() {
        return UpsertViewModel.class;
    }
}