package com.group2.bookstoreproject.data.repositoryImpl;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.group2.bookstoreproject.data.model.Book;
import com.group2.bookstoreproject.data.model.CartItem;
import com.group2.bookstoreproject.data.model.User;
import com.group2.bookstoreproject.data.repository.BookRepository;
import com.group2.bookstoreproject.data.repository.CartItemRepository;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class CartItemRepositoryImpl extends BaseRepositoryImpl<CartItem> implements CartItemRepository {
    private static final String COLLECTION_PATH = "cart_items";
    private final BookRepository bookRepository;
    private static final String TAG = "CartItemRepository";
    private final MutableLiveData<Boolean> cartChangedLiveData = new MutableLiveData<>();

    public CartItemRepositoryImpl() {
        super();
        bookRepository = new BookRepositoryImpl();
        // Listen to changes in the cart items collection
        listenToAllCartItems((snapshots, e) -> cartChangedLiveData.setValue(true));
    }

    public LiveData<Boolean> getCartChangedLiveData() {
        return cartChangedLiveData;
    }
    @Override
    protected String getCollectionPath() {
        return COLLECTION_PATH;
    }

    @Override
    public void getCartItems(String accountId, OnCompleteListener<QuerySnapshot> onCompleteListener) {
        Query query = getCollection(getCollectionPath()).whereEqualTo("accountId", accountId);
        query(query, onCompleteListener);
    }

    @Override
    public void updateQuantity(CartItem item, int quantity, OnCompleteListener<Void> onCompleteListener) {
        bookRepository.getById(item.getBookId(), task -> {
            if (task.isSuccessful()) {
                Book book = task.getResult().toObject(Book.class);
                if (book != null) {
                    Map<String, Object> data = new HashMap<>();
                    data.put("quantity", quantity);
                    data.put("price", book.getPrice() * quantity);

                    getDocument(getCollectionPath(), item.getCartItemId())
                            .update(data)
                            .addOnCompleteListener(onCompleteListener);
                } else {
                    Log.e(TAG, "updateQuantity: book is null");
                }
            } else {
                Log.e(TAG, "updateQuantity: task failed");
            }
        });
    }

    @Override
    public void removeItem(CartItem item, OnCompleteListener<Void> onCompleteListener) {
        getDocument(getCollectionPath(), item.getCartItemId())
                .delete()
                .addOnCompleteListener(onCompleteListener);
    }

    @Override
    public ListenerRegistration listenToCartItems(String accountId, EventListener<QuerySnapshot> eventListener) {
        return getCollection(getCollectionPath())
                .document(accountId)
                .collection("quantity")
                .addSnapshotListener(eventListener);
    }

    @Override
    public ListenerRegistration listenToAllCartItems(EventListener<QuerySnapshot> listener) {
        return getCollection(getCollectionPath())
                .addSnapshotListener(listener);
    }

    @Override
    public void getCartItemByBookAndAccountId(String accountId, String bookId, OnCompleteListener<QuerySnapshot> onCompleteListener) {
        Query query = getCollection(getCollectionPath())
                .whereEqualTo("accountId", accountId)
                .whereEqualTo("bookId", bookId);
        query.get().addOnCompleteListener(onCompleteListener);
    }
}