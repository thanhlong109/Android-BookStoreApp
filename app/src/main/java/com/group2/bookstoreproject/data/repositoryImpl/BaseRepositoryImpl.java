package com.group2.bookstoreproject.data.repositoryImpl;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.group2.bookstoreproject.data.repository.BaseRepository;

public abstract class BaseRepositoryImpl<T> implements BaseRepository<T> {
    protected FirebaseFirestore db;

    public BaseRepositoryImpl() {
        db = FirebaseFirestore.getInstance();
    }

    protected CollectionReference getCollection(String collectionPath) {
        return db.collection(collectionPath);
    }

    protected DocumentReference getDocument(String collectionPath, String documentId) {
        return db.collection(collectionPath).document(documentId);
    }

    @Override
    public void getById(String id, OnCompleteListener<DocumentSnapshot> onCompleteListener) {
        getDocument(getCollectionPath(), id).get().addOnCompleteListener(onCompleteListener);
    }

    @Override
    public void getAll(OnCompleteListener<QuerySnapshot> onCompleteListener) {
        getCollection(getCollectionPath()).get().addOnCompleteListener(onCompleteListener);
    }

    @Override
    public void upsert(String documentId,T item,  OnCompleteListener<Void> onCompleteListener) {
        getCollection(getCollectionPath()).document(documentId).set(item).addOnCompleteListener(onCompleteListener);
    }

    @Override
    public void delete(String id, OnCompleteListener<Void> onCompleteListener) {
        getDocument(getCollectionPath(), id).delete().addOnCompleteListener(onCompleteListener);
    }

    @Override
    public void query(Query query, OnCompleteListener<QuerySnapshot> onCompleteListener) {
        query.get().addOnCompleteListener(onCompleteListener);
    }

    protected abstract String getCollectionPath();
}
