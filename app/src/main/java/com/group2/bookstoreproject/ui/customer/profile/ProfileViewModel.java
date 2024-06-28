package com.group2.bookstoreproject.ui.customer.profile;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.group2.bookstoreproject.base.BaseViewModel;
import com.group2.bookstoreproject.data.model.User;
import com.group2.bookstoreproject.data.model.base.Resource;
import com.group2.bookstoreproject.data.repository.ProfileRepository;
import com.group2.bookstoreproject.data.repositoryImpl.ProfileRepositoryImpl;
import com.group2.bookstoreproject.util.session.SessionManager;
public class ProfileViewModel extends BaseViewModel {

    private final ProfileRepository profileRepository;
    private final MutableLiveData<User> userLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    public ProfileViewModel() {
        this.profileRepository = new ProfileRepositoryImpl();
    }

    public LiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public void loadUserProfile() {
        SessionManager sessionManager = SessionManager.getInstance();
        User loggedInUser = sessionManager.getLoggedInUser();

        if (loggedInUser != null) {
            String email = loggedInUser.getEmail();
            profileRepository.getUserByEmail(email).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    User user = task.getResult();
                    userLiveData.setValue(user);
                } else {
                    errorLiveData.setValue(task.getException().getMessage());
                }
            });
        } else {
            errorLiveData.setValue("No user session found");
        }
    }

    public void updateUserProfile(User updatedUser) {
        profileRepository.updateUser(updatedUser).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                userLiveData.setValue(updatedUser); // Update LiveData with the new user data
            } else {
                errorLiveData.setValue(task.getException().getMessage());
            }
            setLoading(false); // Optional: Update loading state
        });
    }

    public void updateUserAvatar(String imageUrl) {
        User currentUser = userLiveData.getValue();
        if (currentUser != null) {
            currentUser.setAvatar(imageUrl);
            updateUserProfile(currentUser);
        }
    }
}

