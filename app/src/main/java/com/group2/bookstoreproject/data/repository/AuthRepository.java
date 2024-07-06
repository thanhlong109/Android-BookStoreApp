package com.group2.bookstoreproject.data.repository;

import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.group2.bookstoreproject.data.model.User;
import com.group2.bookstoreproject.data.model.base.Resource;

import java.util.List;

import javax.security.auth.callback.Callback;

public interface AuthRepository extends BaseRepository<User> {
    public Task<User> getUserByEmail(String email);
    public Task<User> getUserByUid(String uid);

    public void loadUserDetails(List<String> userIds,OnCompleteListener<DocumentSnapshot> onCompleteListener);
    LiveData<String> getUserFullname(String userId);
}
