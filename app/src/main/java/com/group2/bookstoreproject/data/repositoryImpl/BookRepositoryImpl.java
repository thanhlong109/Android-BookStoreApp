package com.group2.bookstoreproject.data.repositoryImpl;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.group2.bookstoreproject.data.model.Book;
import com.group2.bookstoreproject.data.repository.BookRepository;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class BookRepositoryImpl extends BaseRepositoryImpl<Book> implements BookRepository {
    private static final String COLLECTION_PATH = "books";

    @Override
    protected String getCollectionPath() {
        return COLLECTION_PATH;
    }
    public void getThreeBooksWithHighestStock(OnCompleteListener<QuerySnapshot> onCompleteListener) {
        getCollection(getCollectionPath()).orderBy("stock", Query.Direction.DESCENDING).limit(3).get().addOnCompleteListener(onCompleteListener);
    }

    public void getThreeNewestBooks(OnCompleteListener<QuerySnapshot> onCompleteListener) {
        getCollection(getCollectionPath()).orderBy("bookId", Query.Direction.DESCENDING).limit(3).get().addOnCompleteListener(onCompleteListener);
    }
    public void getThreeBestSellingBooks(OnCompleteListener<QuerySnapshot> onCompleteListener) {
        getCollection(getCollectionPath()).orderBy("sale", Query.Direction.DESCENDING).limit(3).get().addOnCompleteListener(onCompleteListener);
    }


    public void getBooksByIds(List<String> bookIds, OnCompleteListener<QuerySnapshot> onCompleteListener) {
        Query query = db.collection(COLLECTION_PATH).whereIn("bookId", bookIds);
        query.get().addOnCompleteListener(onCompleteListener);
    }
}
