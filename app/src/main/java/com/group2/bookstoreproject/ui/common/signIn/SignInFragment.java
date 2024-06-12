package com.group2.bookstoreproject.ui.common.signIn;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.group2.bookstoreproject.R;
import com.group2.bookstoreproject.base.BaseFragment;
import com.group2.bookstoreproject.databinding.FragmentSignInBinding;
import com.group2.bookstoreproject.ui.activity.CustomerActivity;


public class SignInFragment extends BaseFragment<FragmentSignInBinding, SignInViewModel> {


    @NonNull
    @Override
    protected FragmentSignInBinding inflateBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, boolean attachToParent) {
        return FragmentSignInBinding.inflate(inflater,container,attachToParent);
    }

    @NonNull
    @Override
    protected Class<SignInViewModel> getViewModelClass() {
        return SignInViewModel.class;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnSignIn.setOnClickListener(v -> goToActivity(CustomerActivity.class, true));
    }
}