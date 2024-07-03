package com.group2.bookstoreproject.data.repository;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.QuerySnapshot;
import com.group2.bookstoreproject.data.model.Book;

import java.util.List;

public interface BookRepository extends BaseRepository<Book>{
    void getBooksByIds(List<String> bookIds, OnCompleteListener<QuerySnapshot> onCompleteListener);
}
