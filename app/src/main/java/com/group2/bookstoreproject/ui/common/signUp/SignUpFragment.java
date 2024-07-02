package com.group2.bookstoreproject.ui.common.signUp;

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
import com.group2.bookstoreproject.data.model.Address;
import com.group2.bookstoreproject.data.model.User;
import com.group2.bookstoreproject.data.model.base.Resource;
import com.group2.bookstoreproject.databinding.FragmentSignUpBinding;

public class SignUpFragment extends BaseFragment<FragmentSignUpBinding, SignUpViewModel> {

    @NonNull
    @Override
    protected FragmentSignUpBinding inflateBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, boolean attachToParent) {
        return FragmentSignUpBinding.inflate(inflater, container, attachToParent);
    }

    @NonNull
    @Override
    protected Class<SignUpViewModel> getViewModelClass() {
        return SignUpViewModel.class;
    }

    @Override
    protected void observeViewModel() {
        super.observeViewModel();
        viewModel.getSignUpResult().observe(getViewLifecycleOwner(), new Observer<Resource<Void>>() {
            @Override
            public void onChanged(Resource<Void> resource) {
                if (resource != null) {
                    if (resource.getStatus() == Resource.Status.SUCCESS) {
                        // Sign up successful, now send OTP
                        String phone = binding.txtPhone.getText().toString();
                        viewModel.sendOtp("+84" + phone, getActivity());
                    } else if (resource.getStatus() == Resource.Status.ERROR) {
                        Toast.makeText(getContext(), resource.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        viewModel.getVerificationId().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String verificationId) {
                if (verificationId != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("verificationId", verificationId);
                    bundle.putString("email", binding.txtEmail.getText().toString());
                    navigateToPage(R.id.action_signUpFragment_to_otpFragment, bundle);
                } else {
                    Toast.makeText(getContext(), "Failed to send OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnSignUp.setOnClickListener(v -> {
            String email = binding.txtEmail.getText().toString();
            String password = binding.txtPassword.getText().toString();
            String confirmPassword = binding.txtPasswordConfirm.getText().toString();
            String phone = binding.txtPhone.getText().toString();

            if (!isValidEmail(email)) {
                Toast.makeText(getContext(), "Email không hợp lệ", Toast.LENGTH_SHORT).show();
            } else if (password.length() < 6) {
                Toast.makeText(getContext(), "Mật khẩu phải có ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(confirmPassword)) {
                Toast.makeText(getContext(), "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
            } else {
                User user = new User();
                user.setEmail(email);
                user.setPassword(password);
                user.setRole(2);
                user.setStatus(1);
                user.setPhone(phone);
                user.setUserId(email);
                user.setAddress(new Address());
                viewModel.signUp(user);
            }
        });
        binding.btnGoSignIn.setOnClickListener(v -> navigateToPage(R.id.action_signUpFragment_to_signInFragment));
    }

    private boolean isValidEmail(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
