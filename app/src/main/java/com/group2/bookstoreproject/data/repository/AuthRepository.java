package com.group2.bookstoreproject.data.repository;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.group2.bookstoreproject.data.model.User;
import com.group2.bookstoreproject.data.model.base.Resource;

public interface AuthRepository extends BaseRepository<User> {
    public Task<User> getUserByEmail(String email);
}
