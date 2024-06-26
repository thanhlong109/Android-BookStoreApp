package com.group2.bookstoreproject.ui.common.otp;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.group2.bookstoreproject.base.BaseViewModel;
import com.group2.bookstoreproject.data.model.base.Resource;

public class OtpViewModel extends BaseViewModel {
    private MutableLiveData<Resource<Void>> otpResult = new MutableLiveData<>();
    private String verificationId;
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    public MutableLiveData<Resource<Void>> getOtpResult() {
        return otpResult;
    }

    public void setVerificationId(String verificationId) {
        this.verificationId = verificationId;
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
                        } else {
                            setErrorMessage(task.getException().getMessage());
                        }
                    }
                });
    }
}
