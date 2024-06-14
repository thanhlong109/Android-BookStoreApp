package com.group2.bookstoreproject.ui.common.otp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.group2.bookstoreproject.R;
import com.group2.bookstoreproject.base.BaseFragment;
import com.group2.bookstoreproject.databinding.FragmentOtpBinding;

public class OtpFragment extends BaseFragment<FragmentOtpBinding, OtpViewModel> {


    @NonNull
    @Override
    protected FragmentOtpBinding inflateBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, boolean attachToParent) {
        return FragmentOtpBinding.inflate(inflater,container,attachToParent);
    }

    @NonNull
    @Override
    protected Class<OtpViewModel> getViewModelClass() {
        return OtpViewModel.class;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnGoToSignIn.setOnClickListener(v -> navigateToPage(R.id.action_otpFragment_to_signInFragment));
    }
}