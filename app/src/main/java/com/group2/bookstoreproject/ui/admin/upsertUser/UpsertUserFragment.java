package com.group2.bookstoreproject.ui.admin.upsertUser;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.group2.bookstoreproject.R;
import com.group2.bookstoreproject.base.BaseFragment;
import com.group2.bookstoreproject.databinding.FragmentUserManagerBinding;


public class UpsertUserFragment extends BaseFragment<FragmentUserManagerBinding, UpsertUserViewModel> {


    @NonNull
    @Override
    protected FragmentUserManagerBinding inflateBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, boolean attachToParent) {
        return FragmentUserManagerBinding.inflate(inflater, container, attachToParent);
    }

    @NonNull
    @Override
    protected Class<UpsertUserViewModel> getViewModelClass() {
        return UpsertUserViewModel.class;
    }
}