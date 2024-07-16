package com.group2.bookstoreproject.data.repositoryImpl;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.ListenerRegistration;
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

    @Override
    public ListenerRegistration listenToBooks(EventListener<QuerySnapshot> eventListener) {
        return getCollection(getCollectionPath()).addSnapshotListener(eventListener);
    }

    @Override
    public void getBooksByIds(List<String> bookIds, OnCompleteListener<QuerySnapshot> onCompleteListener) {
        Query query = db.collection(COLLECTION_PATH).whereIn("bookId", bookIds);
        query.get().addOnCompleteListener(onCompleteListener);
    }

    @Override
    public void searchBooksByTitle(String title, OnCompleteListener<QuerySnapshot> onCompleteListener) {
        String searchQuery = title;
        String endQuery = searchQuery + "\uf8ff"; // Ký tự đặc biệt để tạo khoảng tìm kiếm

        Query query = getCollection(getCollectionPath())
                .orderBy("title")
                .startAt(searchQuery)
                .endAt(endQuery);
        query.get().addOnCompleteListener(onCompleteListener);
    }
}
