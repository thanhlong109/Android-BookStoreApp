package com.group2.bookstoreproject.ui.customer.category;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.group2.bookstoreproject.base.BaseViewModel;
import com.group2.bookstoreproject.data.model.Book;
import com.group2.bookstoreproject.data.model.Category;
import com.group2.bookstoreproject.data.repository.BookRepository;
import com.group2.bookstoreproject.data.repository.CategoryRepository;
import com.group2.bookstoreproject.data.repositoryImpl.BookRepositoryImpl;
import com.group2.bookstoreproject.data.repositoryImpl.CategoryRepositoryImpl;
import androidx.lifecycle.Transformations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryViewModel extends BaseViewModel {
    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;
    private final MutableLiveData<Category> selectedCategory = new MutableLiveData<>();
    private final MutableLiveData<List<Category>> categoriesLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Book>> booksLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> searchQuery = new MutableLiveData<>();
    private final LiveData<List<Book>> filteredBooksLiveData;
    private static final String TAG = "CategoryViewModel";

    private List<Book> allBooksList = new ArrayList<>();

    public LiveData<List<Category>> getCategories() {
        return categoriesLiveData;
    }

    public LiveData<Category> getSelectedCategory() {
        return selectedCategory;
    }

    public void selectCategory(Category category) {
        selectedCategory.setValue(category);
    }

    public LiveData<List<Book>> getBooks() {
        return booksLiveData;
    }

    public LiveData<List<Book>> getFilteredBooks() {
        return filteredBooksLiveData;
    }

    public void setSearchQuery(String query) {
        searchQuery.setValue(query);
    }

    public CategoryViewModel() {
        categoryRepository = new CategoryRepositoryImpl();
        bookRepository = new BookRepositoryImpl();

        filteredBooksLiveData = Transformations.switchMap(searchQuery, query -> {
            if (query == null || query.isEmpty()) {
                return booksLiveData;
            } else {
                return Transformations.map(booksLiveData, books -> books.stream()
                        .filter(book -> book.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                                book.getAuthor().toLowerCase().contains(query.toLowerCase()))
                        .collect(Collectors.toList()));
            }
        });
    }

    public void addCategory(Category category) {
        categoryRepository.upsert(category.getCategoryId(), category, task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, "Category added successfully");
            } else {
                Log.e(TAG, "Error adding category", task.getException());
            }
        });
    }

    public void addBook(Book book) {
        bookRepository.upsert(book.getBookId(), book, task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, "Book added successfully");
            } else {
                Log.e(TAG, "Error adding Book", task.getException());
            }
        });
    }

    public void fetchBooks() {
        bookRepository.getAll(task -> {
            if (task.isSuccessful()) {
                allBooksList = task.getResult().toObjects(Book.class);
                booksLiveData.setValue(new ArrayList<>(allBooksList));
            } else {
                Log.e(TAG, "Error fetching books", task.getException());
            }
        });
    }

    public void fetchCategories() {
        categoryRepository.getAll(task -> {
            if (task.isSuccessful()) {
                categoriesLiveData.setValue(task.getResult().toObjects(Category.class));
            } else {
                Log.e("CategoryViewModel", "Error fetching categories", task.getException());
            }
        });
    }

    public void filterByCategory(String categoryName) {
        List<Category> categories = categoriesLiveData.getValue();
        if (categories != null) {
            categories.stream()
                    .filter(category -> category.getName().equalsIgnoreCase(categoryName))
                    .findFirst()
                    .ifPresent(category -> {
                        String categoryId = category.getCategoryId();
                        List<Book> filteredBooks = new ArrayList<>();
                        for (Book book : allBooksList) {
                            if (book.getCategoryId().equals(categoryId)) {
                                filteredBooks.add(book);
                            }
                        }
                        booksLiveData.setValue(filteredBooks);
                    });
        }
    }
    public void filterByPriceIncrease() {
        List<Book> currentBooks = booksLiveData.getValue();
        if (currentBooks != null) {
            currentBooks.sort(Comparator.comparingDouble(Book::getPrice));
            booksLiveData.setValue(currentBooks);
        }
    }

    public void filterByPriceDecrease() {
        List<Book> currentBooks = booksLiveData.getValue();
        if (currentBooks != null) {
            currentBooks.sort((b1, b2) -> Double.compare(b2.getPrice(), b1.getPrice()));
            booksLiveData.setValue(currentBooks);
        }
    }

    public void filterBySaleIncrease() {
        List<Book> currentBooks = booksLiveData.getValue();
        if (currentBooks != null) {
            currentBooks.sort(Comparator.comparingInt(Book::getSale));
            booksLiveData.setValue(currentBooks);
        }
    }

    public void filterBySaleDecrease() {
        List<Book> currentBooks = booksLiveData.getValue();
        if (currentBooks != null) {
            currentBooks.sort((b1, b2) -> Integer.compare(b2.getSale(), b1.getSale()));
            booksLiveData.setValue(currentBooks);
        }
    }

}
