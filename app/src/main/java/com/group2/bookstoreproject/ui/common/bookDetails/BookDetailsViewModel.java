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
        cartItemRepository.upsert(item.getCartItemId(), item, task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, "cart item added successfully");
            } else {
                Log.e(TAG, "Error adding cart item", task.getException());
            }
        });
    }
}
