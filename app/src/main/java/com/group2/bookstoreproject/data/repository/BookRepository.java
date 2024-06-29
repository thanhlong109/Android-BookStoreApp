package com.group2.bookstoreproject.data.repository;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.QuerySnapshot;
import com.group2.bookstoreproject.data.model.Book;

public interface BookRepository extends BaseRepository<Book>{

    public void getThreeBooksWithHighestStock(OnCompleteListener<QuerySnapshot> onCompleteListener);
    public void getThreeNewestBooks(OnCompleteListener<QuerySnapshot> onCompleteListener);
    public void getThreeBestSellingBooks(OnCompleteListener<QuerySnapshot> onCompleteListener);
}
