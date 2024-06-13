package com.group2.bookstoreproject.data.repository;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public interface BaseRepository<T> {
    void getById(String id, OnCompleteListener<DocumentSnapshot> onCompleteListener);
    void getAll(OnCompleteListener<QuerySnapshot> onCompleteListener);
    void upsert(String documentId,T item, OnCompleteListener<Void> onCompleteListener);
    void delete(String id, OnCompleteListener<Void> onCompleteListener);
    void query(Query query, OnCompleteListener<QuerySnapshot> onCompleteListener);
}
