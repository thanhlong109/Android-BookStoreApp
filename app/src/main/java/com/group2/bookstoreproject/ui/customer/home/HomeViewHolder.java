package com.group2.bookstoreproject.ui.customer.home;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;
import com.group2.bookstoreproject.base.BaseViewModel;
import com.group2.bookstoreproject.data.repository.BookRepository;
import com.group2.bookstoreproject.data.repositoryImpl.BookRepositoryImpl;

public class HomeViewHolder extends BaseViewModel {

    private BookRepository bookRepository;

    public HomeViewHolder() {
        bookRepository = new BookRepositoryImpl();
    }

    public void loadNewestBooks(OnCompleteListener<QuerySnapshot> onCompleteListener) {
        bookRepository.getThreeNewestBooks(onCompleteListener);
    }

    public void loadBooksWithHighestStock(OnCompleteListener<QuerySnapshot> onCompleteListener) {
        bookRepository.getThreeBooksWithHighestStock(onCompleteListener);
    }
    public void loadBestSellingBooks(OnCompleteListener<QuerySnapshot> onCompleteListener) {
        bookRepository.getThreeBestSellingBooks(onCompleteListener);
    }
}
