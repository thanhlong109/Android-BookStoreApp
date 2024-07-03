package com.group2.bookstoreproject.ui.common.bookDetails;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.group2.bookstoreproject.base.BaseViewModel;
import com.group2.bookstoreproject.data.model.CartItem;
import com.group2.bookstoreproject.data.repository.CartItemRepository;
import com.group2.bookstoreproject.data.repositoryImpl.CartItemRepositoryImpl;

public class BookDetailsViewModel extends BaseViewModel {
    private final CartItemRepository cartItemRepository ;
    private static final String TAG = "BookDetailViewModel";

    public BookDetailsViewModel() {
        cartItemRepository = new CartItemRepositoryImpl();
    }


    public void addToCart(CartItem item) {
        cartItemRepository.getCartItemByBookAndAccountId(item.getAccountId(), item.getBookId(), task -> {
            if (task.isSuccessful() && task.getResult() != null && !task.getResult().isEmpty()) {
                // Item exists, update the quantity
                CartItem existingItem = task.getResult().getDocuments().get(0).toObject(CartItem.class);
                int newQuantity = existingItem.getQuantity() + 1;
                cartItemRepository.updateQuantity(existingItem, newQuantity, task1 -> {
                    if (!task1.isSuccessful()) {
                        Log.e(TAG, "Error updating cart item", task1.getException());
                    }
                });
            } else {
                // Item does not exist, add a new item
                cartItemRepository.upsert(item.getCartItemId(), item, task1 -> {
                    if (!task1.isSuccessful()) {
                        Log.e(TAG, "Error adding cart item", task1.getException());
                    }
                });
            }
        });
    }
}
