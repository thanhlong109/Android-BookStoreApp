package com.group2.bookstoreproject.data.repositoryImpl;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;
import com.group2.bookstoreproject.data.model.CartItem;
import com.group2.bookstoreproject.data.model.User;
import com.group2.bookstoreproject.data.repository.CartItemRepository;

import java.util.List;
import java.util.function.Consumer;

public class CartItemRepositoryImpl extends BaseRepositoryImpl<CartItem> implements CartItemRepository {

    private static final String COLLECTION_PATH = "cart_items";

    @Override
    protected String getCollectionPath() {
        return COLLECTION_PATH;
    }

    @Override
    public void getCartItems(String accountId, Consumer<List<CartItem>> callback) {
        Query query = getCollection(getCollectionPath()).whereEqualTo("accountId", accountId);
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                List<CartItem> items = task.getResult().toObjects(CartItem.class);
                callback.accept(items);
            } else {
                callback.accept(null);
            }
        });
    }

    @Override
    public void updateQuantity(CartItem item, int quantity, Runnable callback) {
        DocumentReference docRef = getDocument(getCollectionPath(), item.getCartItemId());
        docRef.update("quantity", quantity).addOnCompleteListener(task -> callback.run());
    }

    @Override
    public void removeItem(CartItem item, Runnable callback) {
        DocumentReference docRef = getDocument(getCollectionPath(), item.getCartItemId());
        docRef.delete().addOnCompleteListener(task -> callback.run());
    }
}
