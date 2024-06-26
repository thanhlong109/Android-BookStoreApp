package com.group2.bookstoreproject.ui.customer.cart;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.group2.bookstoreproject.base.BaseViewModel;
import com.group2.bookstoreproject.data.model.CartItem;
import com.group2.bookstoreproject.data.model.base.Resource;
import com.group2.bookstoreproject.data.repository.CartItemRepository;
import com.group2.bookstoreproject.data.repositoryImpl.CartItemRepositoryImpl;

import java.util.List;

public class CartViewModel extends BaseViewModel {
    private final MutableLiveData<List<CartItem>> cartItems;
    private final CartItemRepository cartRepository;

    public CartViewModel() {
        cartRepository = new CartItemRepositoryImpl();
        cartItems = new MutableLiveData<>();
    }

    public LiveData<List<CartItem>> getCartItems(String accountId) {
        loadCartItems(accountId);
        return cartItems;
    }

    private void loadCartItems(String accountId) {
        cartRepository.getCartItems(accountId, cartItems::postValue);
    }

    public void increaseQuantity(CartItem item) {
        cartRepository.updateQuantity(item, item.getQuantity() + 1, () -> loadCartItems(item.getAccountId()));
    }

    public void decreaseQuantity(CartItem item) {
        if (item.getQuantity() > 1) {
            cartRepository.updateQuantity(item, item.getQuantity() - 1, () -> loadCartItems(item.getAccountId()));
        }
    }

    public void removeItem(CartItem item) {
        cartRepository.removeItem(item, () -> loadCartItems(item.getAccountId()));
    }
}
