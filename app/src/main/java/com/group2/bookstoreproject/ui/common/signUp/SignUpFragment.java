package com.group2.bookstoreproject.ui.common.signUp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.group2.bookstoreproject.R;
import com.group2.bookstoreproject.base.BaseFragment;
import com.group2.bookstoreproject.databinding.FragmentSignUpBinding;


public class SignUpFragment extends BaseFragment<FragmentSignUpBinding, SignUpViewModel> {


    @NonNull
    @Override
    protected FragmentSignUpBinding inflateBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, boolean attachToParent) {
        return FragmentSignUpBinding.inflate(inflater,container,attachToParent);
    }

    @NonNull
    @Override
    protected Class<SignUpViewModel> getViewModelClass() {
        return SignUpViewModel.class;
    }
}