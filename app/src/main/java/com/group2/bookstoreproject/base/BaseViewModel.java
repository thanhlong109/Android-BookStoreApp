package com.group2.bookstoreproject.base;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.group2.bookstoreproject.data.sharePreference.AppSharePreference;
import com.group2.bookstoreproject.util.session.SessionManager;

import javax.inject.Inject;
public abstract class BaseViewModel extends ViewModel {

    protected SessionManager sessionManager;

    // LiveData to track loading state
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    // LiveData to track error messages
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public BaseViewModel() {
        // Constructor to inject the dependencies if needed
        sessionManager = SessionManager.getInstance();
    }

    // Getter for loading state
    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    // Setter for loading state
    protected void setLoading(boolean loading) {
        isLoading.setValue(loading);
    }

    // Getter for error message
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    // Setter for error message
    protected void setErrorMessage(String message) {
        errorMessage.setValue(message);
    }


}
