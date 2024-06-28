package com.group2.bookstoreproject.data.repositoryImpl;

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.group2.bookstoreproject.data.model.User;
import com.group2.bookstoreproject.data.repository.ProfileRepository;
import com.group2.bookstoreproject.data.repositoryImpl.BaseRepositoryImpl;

import java.util.HashMap;
import java.util.Map;

public class ProfileRepositoryImpl extends BaseRepositoryImpl<User> implements ProfileRepository {
    private static final String COLLECTION_PATH = "users";

    @Override
    protected String getCollectionPath() {
        return COLLECTION_PATH;
    }

    // Method to get user by email
    @Override
    public Task<User> getUserByEmail(String email) {
        TaskCompletionSource<User> taskCompletionSource = new TaskCompletionSource<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(COLLECTION_PATH)
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        User user = task.getResult().getDocuments().get(0).toObject(User.class);
                        taskCompletionSource.setResult(user);
                    } else {
                        taskCompletionSource.setException(new Exception("User not found or error occurred"));
                    }
                });
        return taskCompletionSource.getTask();
    }

    @Override
    public Task<User> updateUser(User user) {
        TaskCompletionSource<User> taskCompletionSource = new TaskCompletionSource<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Create a map of fields to update
        Map<String, Object> updates = new HashMap<>();
        updates.put("fullName", user.getFullName());
        updates.put("dateOfBirth", user.getDateOfBirth());

        db.collection(COLLECTION_PATH)
                .document(user.getUserId())
                .update(updates)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        taskCompletionSource.setResult(null);
                    } else {
                        taskCompletionSource.setException(task.getException());
                    }
                });

        return taskCompletionSource.getTask();
    }



}
