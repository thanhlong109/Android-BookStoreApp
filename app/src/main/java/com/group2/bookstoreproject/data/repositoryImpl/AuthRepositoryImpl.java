package com.group2.bookstoreproject.data.repositoryImpl;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.group2.bookstoreproject.data.model.User;
import com.group2.bookstoreproject.data.repository.AuthRepository;

public class AuthRepositoryImpl extends BaseRepositoryImpl<User> implements AuthRepository {

    private static final String COLLECTION_PATH = "users";

    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    protected String getCollectionPath() {
        return COLLECTION_PATH;
    }

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
    public Task<User> getUserByUid(String uid) {
        return firestore.collection("users")
                .document(uid)
                .get()
                .continueWith(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        return task.getResult().toObject(User.class);
                    } else {
                        throw task.getException();
                    }
                });
    }
}
