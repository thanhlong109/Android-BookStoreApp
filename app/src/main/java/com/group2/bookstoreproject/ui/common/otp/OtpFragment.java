package com.group2.bookstoreproject.ui.common.otp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.group2.bookstoreproject.R;
import com.group2.bookstoreproject.base.BaseFragment;
import com.group2.bookstoreproject.data.model.base.Resource;
import com.group2.bookstoreproject.databinding.FragmentOtpBinding;
import com.group2.bookstoreproject.ui.activity.CustomerActivity;

public class OtpFragment extends BaseFragment<FragmentOtpBinding, OtpViewModel> {

    @NonNull
    @Override
    protected FragmentOtpBinding inflateBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, boolean attachToParent) {
        return FragmentOtpBinding.inflate(inflater, container, attachToParent);
    }

    @NonNull
    @Override
    protected Class<OtpViewModel> getViewModelClass() {
        return OtpViewModel.class;
    }

    @Override
    protected void observeViewModel() {
        super.observeViewModel();
        viewModel.getOtpResult().observe(getViewLifecycleOwner(), new Observer<Resource<Void>>() {
            @Override
            public void onChanged(Resource<Void> resource) {
                if (resource != null) {
                    if (resource.getStatus() == Resource.Status.SUCCESS) {
                        goToActivity(CustomerActivity.class, true);
                    } else if (resource.getStatus() == Resource.Status.ERROR) {
                        Toast.makeText(getContext(), resource.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            String verificationId = getArguments().getString("verificationId");
            viewModel.setVerificationId(verificationId);
        } else {
            Toast.makeText(getContext(), "Verification ID is missing", Toast.LENGTH_SHORT).show();
            // Handle the error as needed, possibly navigate back or show an error message
        }

        binding.btnVerifyOtp.setOnClickListener(v -> {
            String otp = binding.txtOtp.getText().toString();
            viewModel.verifyOtp(otp);
        });
    }
}
