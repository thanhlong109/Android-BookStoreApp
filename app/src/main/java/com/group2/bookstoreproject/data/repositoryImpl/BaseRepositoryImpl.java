package com.group2.bookstoreproject.data.repositoryImpl;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
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

    // Lắng nghe thời gian thực cho một document cụ thể
    @Override
    public ListenerRegistration listenById(String id, EventListener<DocumentSnapshot> eventListener) {
        return getDocument(getCollectionPath(), id).addSnapshotListener(eventListener);
    }

    // Lắng nghe thời gian thực cho toàn bộ collection
    @Override
    public ListenerRegistration listenAll(EventListener<QuerySnapshot> eventListener) {
        return getCollection(getCollectionPath()).addSnapshotListener(eventListener);
    }

    // Lắng nghe thời gian thực cho một truy vấn cụ thể
    @Override
    public ListenerRegistration listenQuery(Query query, EventListener<QuerySnapshot> eventListener) {
        return query.addSnapshotListener(eventListener);
    }

    protected abstract String getCollectionPath();
}
