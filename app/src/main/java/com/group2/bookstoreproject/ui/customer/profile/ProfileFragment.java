package com.group2.bookstoreproject.ui.customer.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.group2.bookstoreproject.base.BaseFragment;
import com.group2.bookstoreproject.data.model.User;
import com.group2.bookstoreproject.data.repository.ProfileRepository;
import com.group2.bookstoreproject.data.repositoryImpl.ProfileRepositoryImpl;
import com.group2.bookstoreproject.databinding.FragmentProfileBinding;
import com.group2.bookstoreproject.util.session.SessionManager;
import com.group2.bookstoreproject.data.sharePreference.AppSharePreference;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ProfileFragment extends BaseFragment<FragmentProfileBinding, ProfileViewModel> {

    private static final String TAG = "ProfileFragment";
    private ProfileRepository profileRepository;

    @NonNull
    @Override
    protected FragmentProfileBinding inflateBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, boolean attachToParent) {
        return FragmentProfileBinding.inflate(inflater, container, attachToParent);
    }

    @NonNull
    @Override
    protected Class<ProfileViewModel> getViewModelClass() {
        return ProfileViewModel.class;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profileRepository = new ProfileRepositoryImpl();
        loadUserProfile();
    }

    private void loadUserProfile() {
        SessionManager sessionManager = new SessionManager(new AppSharePreference(getContext()));
        User loggedInUser = sessionManager.getLoggedInUser();
        if (loggedInUser != null) {
            String email = loggedInUser.getEmail();
            Log.d(TAG, "User email: " + email);

            profileRepository.getUserByEmail(email).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    User user = task.getResult();
                    if (user != null) {
                        binding.tvFullName.setText(user.getFullName());
                        binding.tvEmail.setText(user.getEmail());
                        binding.tvPhone.setText(user.getPhone());

                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        String birthDateString = sdf.format(new Date(user.getDateOfBirth()));
                        binding.tvBirthdate.setText(birthDateString);
                    } else {
                        Log.d(TAG, "User not found");
                    }
                } else {
                    Log.e(TAG, "Error getting user", task.getException());
                }
            });
        } else {
            Log.d(TAG, "No user session found");
        }
    }
}
