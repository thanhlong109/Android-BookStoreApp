package com.group2.bookstoreproject.ui.common.signIn;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.group2.bookstoreproject.base.BaseViewModel;
import com.group2.bookstoreproject.data.model.User;
import com.group2.bookstoreproject.data.model.base.Resource;
import com.group2.bookstoreproject.data.repository.AuthRepository;
import com.group2.bookstoreproject.data.repositoryImpl.AuthRepositoryImpl;
import com.group2.bookstoreproject.ui.customer.cart.CartFragment;

public class SignInViewModel extends BaseViewModel {
    private MutableLiveData<Resource<Void>> signInResult = new MutableLiveData<>();
    private AuthRepository authRepository = new AuthRepositoryImpl();
    private final MutableLiveData<Resource<User>> userLiveData = new MutableLiveData<>();

    public LiveData<Resource<User>> getUserLiveData() {
        return userLiveData;
    }

    public MutableLiveData<Resource<Void>> getSignInResult() {
        return signInResult;
    }

    public void signIn(Context context, String email, String password) {
        setLoading(true);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        Log.d("emailpassword", "email and pass word: " + email +"  "+ password);
        Log.d("auth", "email and pass word: " + auth);
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        setLoading(false);
                        if (task.isSuccessful()) {
                            // Log success message
                            Log.d("SignInViewModel", "User signed in successfully: " + email);
                            // User signed in successfully, now retrieve user info from Firestore
                            authRepository.getUserByEmail(email)
                                    .addOnCompleteListener(new OnCompleteListener<User>() {
                                        @Override
                                        public void onComplete(@NonNull Task<User> userTask) {
                                            if (userTask.isSuccessful() && userTask.getResult() != null) {
                                                userLiveData.setValue(Resource.success(userTask.getResult()));
                                                // Log success message
                                                // Log.d("SignInViewModel", "User data fetched successfully for: " + email);
                                                // Store account ID in SharedPreferences
                                                User user = userTask.getResult();
                                                SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                                editor.putString("accountId", user.getUserId());
                                                editor.apply();

                                            } else {
                                                setErrorMessage("Failed to fetch user data.");
                                                // Log error message
                                                //Log.e("SignInViewModel", "Failed to fetch user data for: " + email);
                                            }
                                        }
                                    });
                        } else {
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthInvalidUserException e) {
                                setErrorMessage("The user does not exist.");
                                // Show error toast
                                Toast.makeText(context, "The user does not exist.", Toast.LENGTH_SHORT).show();
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                setErrorMessage("Invalid credentials.");
                                // Show error toast
                                Toast.makeText(context, "Invalid credentials.", Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                setErrorMessage("Authentication failed.");
                                // Show error toast
                                Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}