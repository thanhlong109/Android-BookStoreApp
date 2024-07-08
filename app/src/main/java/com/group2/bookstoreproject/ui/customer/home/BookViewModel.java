package com.group2.bookstoreproject.ui.customer.home;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.ListenerRegistration;
import com.group2.bookstoreproject.base.BaseViewModel;
import com.group2.bookstoreproject.data.model.Book;
import com.group2.bookstoreproject.data.model.CartItem;
import com.group2.bookstoreproject.data.repository.BookRepository;
import com.group2.bookstoreproject.data.repository.CartItemRepository;
import com.group2.bookstoreproject.data.repositoryImpl.BookRepositoryImpl;
import com.group2.bookstoreproject.data.repositoryImpl.CartItemRepositoryImpl;

import java.util.List;

public class BookViewModel extends BaseViewModel {
    private final MutableLiveData<List<Book>> books;
    private final BookRepository bookRepository;
    private ListenerRegistration bookLisiener;
    private static final String TAG = "BookViewModel";

    public BookViewModel(MutableLiveData<List<Book>> books, BookRepository bookRepository) {
        this.books = books;
        this.bookRepository = bookRepository;

    }

    public BookViewModel() {
        bookRepository = new BookRepositoryImpl();
        books = new MutableLiveData<>();
    }



}
