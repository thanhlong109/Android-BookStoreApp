package com.group2.bookstoreproject.ui.common.signIn;

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
import com.group2.bookstoreproject.data.model.Shipping;
import com.group2.bookstoreproject.data.model.User;
import com.group2.bookstoreproject.data.model.base.Resource;
import com.group2.bookstoreproject.databinding.FragmentSignInBinding;
import com.group2.bookstoreproject.ui.activity.AdminActivity;
import com.group2.bookstoreproject.ui.activity.CustomerActivity;
import com.group2.bookstoreproject.ui.activity.ShipperActivity;


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
    protected void observeViewModel() {
        super.observeViewModel();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        //binding.btnClient.setOnClickListener(v -> goToActivity(CustomerActivity.class));
        //binding.btnAdmin.setOnClickListener(v -> goToActivity(AdminActivity.class));
        //binding.btnGoToShip.setOnClickListener(v -> goToActivity(ShipperActivity.class));
        //binding.btnGoSignUp.setOnClickListener(v -> navigateToPage(R.id.action_signInFragment_to_signUpFragment));
    }
}