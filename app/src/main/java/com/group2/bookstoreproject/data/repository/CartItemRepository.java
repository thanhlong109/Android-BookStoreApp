package com.group2.bookstoreproject.data.repository;

import com.group2.bookstoreproject.data.model.CartItem;
import com.google.android.gms.tasks.Task;

import java.util.List;
import java.util.function.Consumer;

public interface CartItemRepository extends BaseRepository<CartItem> {
    void getCartItems(String accountId, Consumer<List<CartItem>> callback);
    void updateQuantity(CartItem item, int quantity, Runnable callback);
    void removeItem(CartItem item, Runnable callback);
}
