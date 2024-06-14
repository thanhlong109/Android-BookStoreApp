package com.group2.bookstoreproject.ui.common.signUp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.group2.bookstoreproject.R;
import com.group2.bookstoreproject.base.BaseFragment;
import com.group2.bookstoreproject.data.model.User;
import com.group2.bookstoreproject.data.model.base.Resource;
import com.group2.bookstoreproject.databinding.FragmentSignUpBinding;
import com.group2.bookstoreproject.ui.activity.CustomerActivity;


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

    @Override
    protected void observeViewModel() {
        super.observeViewModel();
        viewModel.getSignUpResult().observe(getViewLifecycleOwner(), new Observer<Resource<Void>>() {
            @Override
            public void onChanged(Resource<Void> resource) {
                if(resource !=null){
                    if(resource.getStatus() == Resource.Status.SUCCESS){
                        goToActivity(CustomerActivity.class, true);
                    }
                }
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        super.onViewCreated(view, savedInstanceState);
        User u = new User();
        u.setEmail("long@gmail.com");
        u.setPassword("123");

        binding.btnSignUp.setOnClickListener(v ->viewModel.signUp(u));
        binding.btnOtp.setOnClickListener(v -> navigateToPage(R.id.action_signUpFragment_to_otpFragment));
    }
}