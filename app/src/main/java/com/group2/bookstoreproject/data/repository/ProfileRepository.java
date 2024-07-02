package com.group2.bookstoreproject.data.repository;

import android.provider.ContactsContract;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.group2.bookstoreproject.data.model.User;

public interface ProfileRepository extends BaseRepository<User>{
    public Task<User> getUserByEmail(String email);
    public Task<User> updateUser(User user);
    public Task<User> updateImgUser(User user);

    void listenToUserChanges(String userId, EventListener<DocumentSnapshot> listener);

}
