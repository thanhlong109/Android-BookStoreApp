package com.group2.bookstoreproject.data.repository;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;
import com.group2.bookstoreproject.data.model.Book;

public interface BookRepository extends BaseRepository<Book>{
    ListenerRegistration listenToBooks(EventListener<QuerySnapshot> eventListener);
}
