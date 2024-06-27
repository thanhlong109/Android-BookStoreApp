package com.group2.bookstoreproject.ui.customer.profile;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.group2.bookstoreproject.base.BaseFragment;
import com.group2.bookstoreproject.databinding.FragmentProfileBinding;

public class UpdateProfileFragment extends BaseFragment<FragmentProfileBinding, UpdateProfileViewModel> {
    @NonNull
    @Override
    protected FragmentProfileBinding inflateBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, boolean attachToParent) {
        return FragmentProfileBinding.inflate(inflater, container,attachToParent);
    }

    @NonNull
    @Override
    protected Class<UpdateProfileViewModel> getViewModelClass() {
        return UpdateProfileViewModel.class;
    }
}
