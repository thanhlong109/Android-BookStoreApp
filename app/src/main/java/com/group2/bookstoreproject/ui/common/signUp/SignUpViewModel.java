package com.group2.bookstoreproject.ui.common.signUp;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.group2.bookstoreproject.base.BaseViewModel;
import com.group2.bookstoreproject.data.model.User;
import com.group2.bookstoreproject.data.model.base.Resource;
import com.group2.bookstoreproject.data.repository.AuthRepository;
import com.group2.bookstoreproject.data.repositoryImpl.AuthRepositoryImpl;

public class SignUpViewModel extends BaseViewModel {

    private MutableLiveData<Resource<Void>> signUpResult;

    public SignUpViewModel() {
        signUpResult = new MutableLiveData<>();
    }

    public MutableLiveData<Resource<Void>> getSignUpResult() {
        return signUpResult;
    }

    AuthRepository authRepository = new AuthRepositoryImpl();
    public void signUp(User user){
        setLoading(true);
        authRepository.upsert(user.getEmail(),user, new OnCompleteListener<Void>(){
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    signUpResult.setValue(Resource.success(null));
                }else{
                    setErrorMessage(task.getException().getMessage());
                }
                setLoading(false);
            }
        });

    }
}
