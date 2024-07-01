package com.group2.bookstoreproject.ui.common.otp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.group2.bookstoreproject.R;
import com.group2.bookstoreproject.base.BaseFragment;
import com.group2.bookstoreproject.data.model.User;
import com.group2.bookstoreproject.data.model.base.Resource;
import com.group2.bookstoreproject.data.sharePreference.AppSharePreference;
import com.group2.bookstoreproject.databinding.FragmentOtpBinding;
import com.group2.bookstoreproject.ui.activity.AdminActivity;
import com.group2.bookstoreproject.ui.activity.CustomerActivity;
import com.group2.bookstoreproject.ui.activity.ShipperActivity;
import com.group2.bookstoreproject.util.session.SessionManager;

public class OtpFragment extends BaseFragment<FragmentOtpBinding, OtpViewModel> {

    private static final String TAG = "OtpFragment";

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
                    Log.d(TAG, "OTP Result status: " + resource.getStatus());
                    if (resource.getStatus() == Resource.Status.SUCCESS) {
                        // OTP verified successfully, user data will be fetched next
                        Log.d(TAG, "OTP verified successfully");
                    } else if (resource.getStatus() == Resource.Status.ERROR) {
                        Toast.makeText(getContext(), resource.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "OTP verification failed: " + resource.getMessage());
                    }
                }
            }
        });

        viewModel.getUserLiveData().observe(getViewLifecycleOwner(), new Observer<Resource<User>>() {
            @Override
            public void onChanged(Resource<User> resource) {
                if (resource != null) {
                    Log.d(TAG, "User data status: " + resource.getStatus());
                    if (resource.getStatus() == Resource.Status.SUCCESS) {
                        User user = resource.getData();
                        if (user != null) {
                            SessionManager sessionManager = new SessionManager(new AppSharePreference(getContext()));
                            sessionManager.saveUser(user);
                            navigateToActivity(user.getRole());
                            Log.d(TAG, "User data fetched and saved, navigating to activity for role: " + user.getRole());
                        } else {
                            Toast.makeText(getContext(), "Thông tin người dùng rỗng", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "User data is null");
                        }
                    } else if (resource.getStatus() == Resource.Status.ERROR) {
                        Toast.makeText(getContext(), resource.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Failed to fetch user data: " + resource.getMessage());
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
            String email = getArguments().getString("email");
            viewModel.setVerificationId(verificationId);
            viewModel.setEmail(email);
            Log.d(TAG, "Verification ID set: " + verificationId);
            Log.d(TAG, "Email received: " + email);
        } else {
            Toast.makeText(getContext(), "Verification ID is missing", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Verification ID is missing");
        }

        binding.btnVerifyOtp.setOnClickListener(v -> {
            String otp = binding.txtOtp.getText().toString();
            Log.d(TAG, "OTP entered: " + otp);
            viewModel.verifyOtp(otp);
        });
    }


    private void navigateToActivity(int role) {
        Class<?> activityClass;
        switch (role) {
            case 1: // Admin
                activityClass = AdminActivity.class;
                break;
            case 2: // Customer
                activityClass = CustomerActivity.class;
                break;
            case 3: // Shipper
                activityClass = ShipperActivity.class;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + role);
        }
        Intent intent = new Intent(getContext(), activityClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        requireActivity().finish();
    }
}
