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
import com.group2.bookstoreproject.data.model.User;
import com.group2.bookstoreproject.data.model.base.Resource;
import com.group2.bookstoreproject.databinding.FragmentSignUpBinding;
import com.group2.bookstoreproject.ui.activity.AuthActivity;
import com.group2.bookstoreproject.ui.activity.CustomerActivity;

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

        binding.btnSignUp.setOnClickListener(v -> {
            String email = binding.txtEmail.getText().toString();
            String password = binding.txtPassword.getText().toString();
            String confirmPassword = binding.txtPasswordConfirm.getText().toString();

            if (password.equals(confirmPassword)) {
                User user = new User();
                user.setEmail(email);
                user.setPassword(password);
                user.setRole(2);
                viewModel.signUp(user);
            } else {
                Toast.makeText(getContext(), "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnGoSignIn.setOnClickListener(v -> navigateToPage(R.id.action_signInFragment_to_signUpFragment));
        binding.btnOtp.setOnClickListener(v -> navigateToPage(R.id.action_signUpFragment_to_otpFragment));
    }
}
