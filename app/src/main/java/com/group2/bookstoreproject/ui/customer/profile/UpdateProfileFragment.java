package com.group2.bookstoreproject.ui.customer.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.group2.bookstoreproject.base.BaseFragment;
import com.group2.bookstoreproject.data.model.User;
import com.group2.bookstoreproject.databinding.FragmentUpdateProfileBinding;

public class UpdateProfileFragment extends BaseFragment<FragmentUpdateProfileBinding, UpdateProfileViewModel> {

    @Override
    protected FragmentUpdateProfileBinding inflateBinding(LayoutInflater inflater, ViewGroup container, boolean attachToParent) {
        return FragmentUpdateProfileBinding.inflate(inflater, container, attachToParent);
    }

    @Override
    protected Class<UpdateProfileViewModel> getViewModelClass() {
        return UpdateProfileViewModel.class;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Observe user data from ViewModel
        viewModel.getUserLiveData().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user != null) {
                    // Populate UI fields with user data
                    binding.etUsername.setText(user.getFullName());
                    // Set Date of Birth if applicable
                    if (user.getDateOfBirth() != 0) {
                        // Assuming user.getDateOfBirth() returns a long value
                        binding.etBirthdate.setText(String.valueOf(user.getDateOfBirth()));
                    }
                }
            }
        });

        binding.btnUpdate.setOnClickListener(v -> {
            // Get updated values from UI
            String fullName = binding.etUsername.getText().toString().trim();
            String birthdateString = binding.etBirthdate.getText().toString().trim();

            if (!birthdateString.isEmpty()) {
                long birthdate = Long.parseLong(birthdateString);
                // Validate input if needed

                // Call ViewModel method to update user data
                viewModel.updateUserProfile(fullName, birthdate);
            } else {
                // Handle case where birthdate is empty
            }
        });
    }
}
