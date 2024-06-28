package com.group2.bookstoreproject.ui.customer.profile;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.group2.bookstoreproject.base.BaseViewModel;
import com.group2.bookstoreproject.data.model.User;
import com.group2.bookstoreproject.data.repository.ProfileRepository;
import com.group2.bookstoreproject.data.repositoryImpl.ProfileRepositoryImpl;

public class UpdateProfileViewModel extends BaseViewModel {

    private ProfileRepository profileRepository;
    private MutableLiveData<User> userLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>();

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
        // Construct or update User object
        User user = new User(fullName, birthdate); // Assuming other fields like email are not updated here

        profileRepository.upsert(user.getUserId(), user, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    userLiveData.setValue(user); // Update LiveData with the new user data
                } else {
                    errorLiveData.setValue(task.getException().getMessage());
                }
                setLoading(false); // Optional: Update loading state
            }
        });
    }
}

