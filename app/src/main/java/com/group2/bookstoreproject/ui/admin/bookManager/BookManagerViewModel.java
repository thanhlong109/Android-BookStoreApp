package com.group2.bookstoreproject.ui.admin.bookManager;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.ListenerRegistration;
import com.group2.bookstoreproject.base.BaseViewModel;
import com.group2.bookstoreproject.data.model.Book;
import com.group2.bookstoreproject.data.repository.BookRepository;
import com.group2.bookstoreproject.data.repositoryImpl.BookRepositoryImpl;

import java.util.List;

public class BookManagerViewModel extends BaseViewModel {
    private final MutableLiveData<List<Book>> books;
    private final BookRepository bookRepository;
    private ListenerRegistration booksListener;
    private static final String TAG = "BookManagerViewModel";

    public BookManagerViewModel() {
        bookRepository = new BookRepositoryImpl();
        books = new MutableLiveData<>();
    }

    public LiveData<List<Book>> getBooks() {
        return books;
    }

    public void loadBooks() {
        setLoading(true);
        stopListeningToBooks();
        bookRepository.getAll(task -> {
            if (task.isSuccessful()) {
                books.setValue(task.getResult().toObjects(Book.class));
            } else {
                Log.e(TAG, "Error loading books", task.getException());
            }
        });
        startListeningToBooks();
        setLoading(false);
    }

    public void addBook(Book book) {
        bookRepository.upsert(book.getBookId(), book, task -> {
            if (!task.isSuccessful()) {
                Log.e(TAG, "Error adding book", task.getException());
            }
        });
    }

    public void updateBook(Book book) {
        bookRepository.upsert(book.getBookId(), book, task -> {
            if (!task.isSuccessful()) {
                Log.e(TAG, "Error updating book", task.getException());
            }
        });
    }

    public void deleteBook(String bookId) {
        bookRepository.delete(bookId, task -> {
            if (!task.isSuccessful()) {
                Log.e(TAG, "Error deleting book", task.getException());
            }
        });
    }

    private void startListeningToBooks() {
        booksListener = bookRepository.listenToBooks((querySnapshot, e) -> {
            if (e != null) {
                Log.e("BookManagerViewModel", "Listen failed", e);
                return;
            }
            if (querySnapshot != null) {
                books.setValue(querySnapshot.toObjects(Book.class));
            }
        });
    }

    public void stopListeningToBooks() {
        if (booksListener != null) {
            booksListener.remove();
        }
    }
}
