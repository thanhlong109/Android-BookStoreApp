package com.group2.bookstoreproject.ui.common.signUp;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.group2.bookstoreproject.base.BaseViewModel;
import com.group2.bookstoreproject.data.model.User;
import com.group2.bookstoreproject.data.model.base.Resource;
import com.group2.bookstoreproject.data.repository.AuthRepository;
import com.group2.bookstoreproject.data.repositoryImpl.AuthRepositoryImpl;

import java.util.concurrent.TimeUnit;

public class SignUpViewModel extends BaseViewModel {
    private static final String TAG = "SignUpViewModel";
    private MutableLiveData<Resource<Void>> signUpResult = new MutableLiveData<>();
    private MutableLiveData<String> verificationId = new MutableLiveData<>();
    private AuthRepository authRepository = new AuthRepositoryImpl();
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    public MutableLiveData<Resource<Void>> getSignUpResult() {
        return signUpResult;
    }

    public MutableLiveData<String> getVerificationId() {
        return verificationId;
    }

    public void signUp(User user){
        setLoading(true);
        auth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // User successfully created with Firebase Authentication
                            user.setUserId(auth.getCurrentUser().getUid()); // Setting Firebase user ID
                            // Now save the user data to Firestore
                            authRepository.upsert(user.getEmail(), user, new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    setLoading(false);
                                    if (task.isSuccessful()) {
                                        signUpResult.setValue(Resource.success(null));
                                    } else {
                                        setErrorMessage(task.getException().getMessage());
                                    }
                                }
                            });
                        } else {
                            setLoading(false);
                            setErrorMessage(task.getException().getMessage());
                        }
                    }
                });
    }

    public void sendOtp(String phoneNumber, Activity activity) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60L,
                TimeUnit.SECONDS,
                activity,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        // This callback will be invoked when the verification is automatically completed
                        // You can directly sign in with this credential if needed
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        setErrorMessage(e.getMessage());
                    }

                    @Override
                    public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        SignUpViewModel.this.verificationId.setValue(verificationId);
                    }
                }
        );
    }
}
