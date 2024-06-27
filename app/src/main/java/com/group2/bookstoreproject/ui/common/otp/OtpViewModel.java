package com.group2.bookstoreproject.ui.common.otp;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.group2.bookstoreproject.base.BaseViewModel;
import com.group2.bookstoreproject.data.model.User;
import com.group2.bookstoreproject.data.model.base.Resource;
import com.group2.bookstoreproject.data.repository.AuthRepository;
import com.group2.bookstoreproject.data.repositoryImpl.AuthRepositoryImpl;

public class OtpViewModel extends BaseViewModel {
    private static final String TAG = "OtpViewModel";
    private MutableLiveData<Resource<Void>> otpResult = new MutableLiveData<>();
    private MutableLiveData<Resource<User>> userLiveData = new MutableLiveData<>();
    private String verificationId;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private AuthRepository authRepository = new AuthRepositoryImpl();

    public MutableLiveData<Resource<Void>> getOtpResult() {
        return otpResult;
    }

    public MutableLiveData<Resource<User>> getUserLiveData() {
        return userLiveData;
    }

    public void setVerificationId(String verificationId) {
        this.verificationId = verificationId;
    }

    private String email;

    public void setEmail(String email) {
        this.email = email;
    }

    public void verifyOtp(String otp) {
        setLoading(true);
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otp);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        setLoading(false);
                        if (task.isSuccessful()) {
                            otpResult.setValue(Resource.success(null));
                            // Fetch user details after successful OTP verification
                            fetchUserByEmail(email);
                        } else {
                            setErrorMessage(task.getException().getMessage());
                        }
                    }
                });
    }


    private void fetchUserByEmail(String email) {
        authRepository.getUserByEmail(email).addOnCompleteListener(new OnCompleteListener<User>() {
            @Override
            public void onComplete(@NonNull Task<User> userTask) {
                if (userTask.isSuccessful() && userTask.getResult() != null) {
                    userLiveData.setValue(Resource.success(userTask.getResult()));
                } else {
                    setErrorMessage("Failed to fetch user data");
                }
            }
        });
    }
}
