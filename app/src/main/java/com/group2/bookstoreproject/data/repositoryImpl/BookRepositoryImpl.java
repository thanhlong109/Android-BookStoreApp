package com.group2.bookstoreproject.data.repositoryImpl;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.group2.bookstoreproject.data.model.Book;
import com.group2.bookstoreproject.data.repository.BookRepository;

import java.util.List;

public class BookRepositoryImpl extends BaseRepositoryImpl<Book> implements BookRepository {
    private static final String COLLECTION_PATH = "books";

    @Override
    protected String getCollectionPath() {
        return COLLECTION_PATH;
    }

    @Override
    public ListenerRegistration listenToBooks(EventListener<QuerySnapshot> eventListener) {
        return getCollection(getCollectionPath()).addSnapshotListener(eventListener);
    }

    @Override
    public void getBooksByIds(List<String> bookIds, OnCompleteListener<QuerySnapshot> onCompleteListener) {
        Query query = db.collection(COLLECTION_PATH).whereIn("bookId", bookIds);
        query.get().addOnCompleteListener(onCompleteListener);
    }
}
