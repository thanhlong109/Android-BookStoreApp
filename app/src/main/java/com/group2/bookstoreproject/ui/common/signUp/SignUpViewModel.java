package com.group2.bookstoreproject.ui.common.signUp;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.group2.bookstoreproject.base.BaseViewModel;
import com.group2.bookstoreproject.data.model.User;
import com.group2.bookstoreproject.data.model.base.Resource;
import com.group2.bookstoreproject.data.repository.AuthRepository;
import com.group2.bookstoreproject.data.repositoryImpl.AuthRepositoryImpl;

public class SignUpViewModel extends BaseViewModel {
    private MutableLiveData<Resource<Void>> signUpResult = new MutableLiveData<>();
    private AuthRepository authRepository = new AuthRepositoryImpl();

    public MutableLiveData<Resource<Void>> getSignUpResult() {
        return signUpResult;
    }

    public void signUp(User user){
        setLoading(true);
        FirebaseAuth auth = FirebaseAuth.getInstance();
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
}
