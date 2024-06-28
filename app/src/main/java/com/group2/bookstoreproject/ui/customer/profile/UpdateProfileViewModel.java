package com.group2.bookstoreproject.ui.customer.profile;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.group2.bookstoreproject.base.BaseViewModel;
import com.group2.bookstoreproject.data.model.User;
import com.group2.bookstoreproject.data.repository.ProfileRepository;
import com.group2.bookstoreproject.data.repositoryImpl.ProfileRepositoryImpl;

public class UpdateProfileViewModel extends BaseViewModel {

    private final ProfileRepository profileRepository;
    private final MutableLiveData<User> userLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    public UpdateProfileViewModel() {
        this.profileRepository = new ProfileRepositoryImpl();
    }

    public LiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public void updateUserProfile(String fullName, long birthdate) {
        User currentUser = userLiveData.getValue();
        if (currentUser != null) {
            User updatedUser = new User(currentUser.getUserId(), fullName, birthdate);
            Log.d("User", "Updating profile for: " + updatedUser.getFullName());

            profileRepository.updateUser(updatedUser).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    userLiveData.setValue(updatedUser);
                } else {
                    errorLiveData.setValue(task.getException().getMessage());
                }
                setLoading(false);
            });
        } else {
            Log.e("UpdateProfileViewModel", "Current user is null");
            errorLiveData.setValue("User data is not available");
        }
    }
}
