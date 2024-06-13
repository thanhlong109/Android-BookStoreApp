package com.group2.bookstoreproject.data.repositoryImpl;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentReference;
import com.group2.bookstoreproject.data.model.User;
import com.group2.bookstoreproject.data.repository.AuthRepository;

public class AuthRepositoryImpl extends BaseRepositoryImpl<User> implements AuthRepository {

    private static final String COLLECTION_PATH = "users";

    @Override
    protected String getCollectionPath() {
        return COLLECTION_PATH;
    }

}
