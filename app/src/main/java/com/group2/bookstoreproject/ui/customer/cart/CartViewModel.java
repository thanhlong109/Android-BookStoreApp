package com.group2.bookstoreproject.ui.customer.cart;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.group2.bookstoreproject.base.BaseViewModel;
import com.group2.bookstoreproject.data.model.CartItem;
import com.group2.bookstoreproject.data.model.ChatMessage;
import com.group2.bookstoreproject.data.repository.BookRepository;
import com.group2.bookstoreproject.data.repository.CartItemRepository;
import com.group2.bookstoreproject.data.repositoryImpl.BookRepositoryImpl;
import com.group2.bookstoreproject.data.repositoryImpl.CartItemRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

public class CartViewModel extends BaseViewModel {
    private final MutableLiveData<List<CartItem>> cartItems;
    private final CartItemRepository cartRepository;
    private ListenerRegistration cartItemsListener;
    private final MutableLiveData<Boolean> cartChangedLiveData;
    private static final String TAG = "CartViewModel";

    public CartViewModel() {
        cartRepository = new CartItemRepositoryImpl();
        cartItems = new MutableLiveData<>();
        cartChangedLiveData = (MutableLiveData<Boolean>) ((CartItemRepositoryImpl) cartRepository).getCartChangedLiveData();
    }

    public LiveData<List<CartItem>> getCartItems() {
        return cartItems;
    }
    public LiveData<Boolean> getCartChangedLiveData() {return cartChangedLiveData;}

    public void loadCartItems(String accountId) {
        setLoading(true);
        stopListeningToCartItems();
        cartRepository.getCartItems(accountId, task -> {
            if (task.isSuccessful()) {
                cartItems.setValue(task.getResult().toObjects(CartItem.class));
            } else {
                Log.e(TAG, "Error loading cart items", task.getException());
            }
        });
        startListeningToCartItems(accountId);
        setLoading(false);
    }

    public void increaseQuantity(CartItem item) {
        cartRepository.updateQuantity(item, item.getQuantity() + 1, task -> {
            if (task.isSuccessful()) {
                updateCartItem(item, item.getQuantity() + 1);
            } else {
                Log.e(TAG, "Error increasing quantity", task.getException());
            }
        });
    }

    public void decreaseQuantity(CartItem item) {
        cartRepository.updateQuantity(item, item.getQuantity() - 1, task -> {
            if (task.isSuccessful()) {
                updateCartItem(item, item.getQuantity() - 1);
            } else {
                Log.e(TAG, "Error decreasing quantity", task.getException());
            }
        });
    }

    public void removeItem(CartItem item) {
        cartRepository.removeItem(item, task -> {
            if (task.isSuccessful()) {
                removeCartItem(item);
            } else {
                Log.e(TAG, "Error removing item", task.getException());
            }
        });
    }

    private void startListeningToCartItems(String accountId) {
        cartItemsListener = cartRepository.listenToCartItems(accountId, (querySnapshot, e) -> {
            if (e != null) {
                Log.e(TAG, "Listen failed", e);
                return;
            }
            if (querySnapshot != null) {
                cartItems.setValue(querySnapshot.toObjects(CartItem.class));
            } else {
                Log.d(TAG, "Current data: null");
            }
        });
    }

    public void stopListeningToCartItems() {
        if (cartItemsListener != null) {
            cartItemsListener.remove();
        }
    }

    private void updateCartItem(CartItem item, int newQuantity) {
        List<CartItem> currentItems = cartItems.getValue();
        if (currentItems != null) {
            for (CartItem cartItem : currentItems) {
                if (cartItem.getCartItemId().equals(item.getCartItemId())) {
                    cartItem.setQuantity(newQuantity);
                    break;
                }
            }
            cartItems.setValue(currentItems);
        }
    }

    private void removeCartItem(CartItem item) {
        List<CartItem> currentItems = cartItems.getValue();
        if (currentItems != null) {
            currentItems.removeIf(cartItem -> cartItem.getCartItemId().equals(item.getCartItemId()));
            cartItems.setValue(currentItems);
        }
    }

    public void listenToCartItemInCart(String cartItemId) {
        cartItemsListener = cartRepository.listenToCartItems(cartItemId, (querySnapshot, e) -> {
            if (e != null) {
                Log.d(TAG, "Listen failed.", e);
                return;
            }
            if (querySnapshot != null) {
                List<CartItem> currentItems = cartItems.getValue();
                if (currentItems == null) {
                    currentItems = new ArrayList<>();
                }
                for (DocumentChange dc : querySnapshot.getDocumentChanges()) {
                    switch (dc.getType()) {
                        case ADDED:
                            Log.d(TAG, "New message: " + dc.getDocument().getData());
                            currentItems.add(dc.getDocument().toObject(CartItem.class));
                            break;
                        case MODIFIED:
                            Log.d(TAG, "Modified message: " + dc.getDocument().getData());
                            CartItem modifiedCartItem = dc.getDocument().toObject(CartItem.class);
                            // Update the existing cart item in the list
                            for (int i = 0; i < currentItems.size(); i++) {
                                if (currentItems.get(i).getCartItemId().equals(modifiedCartItem.getCartItemId())) {
                                    currentItems.set(i, modifiedCartItem);
                                    break;
                                }
                            }
                            break;
                        case REMOVED:
                            Log.d(TAG, "Cart item removed: " + dc.getDocument().getData());
                            // Handle removal if needed
                            currentItems.removeIf(cartItem -> cartItem.getCartItemId().equals(dc.getDocument().getId()));
                            break;
                    }
                }
                cartItems.setValue(currentItems);
            } else {
                Log.d(TAG, "Current data: null");
            }
        });
    }

    public void listenToAllCartItems() {
        cartItemsListener = cartRepository.listenToAllCartItems((querySnapshot, error) -> {
            if (error != null) {
                Log.d(TAG, "Listen failed.", error);
                return;
            }
            if (querySnapshot != null) {
                List<CartItem> currentItems = cartItems.getValue();
                if (currentItems == null) {
                    currentItems = new ArrayList<>();
                }
                for (DocumentChange dc : querySnapshot.getDocumentChanges()) {
                    switch (dc.getType()) {
                        case ADDED:
                            Log.d(TAG, "New cart item added: " + dc.getDocument().getData());
                            currentItems.add(dc.getDocument().toObject(CartItem.class));
                            break;
                        case MODIFIED:
                            Log.d(TAG, "Cart item modified: " + dc.getDocument().getData());
                            CartItem modifiedCartItem = dc.getDocument().toObject(CartItem.class);
                            for (int i = 0; i < currentItems.size(); i++) {
                                if (currentItems.get(i).getCartItemId().equals(modifiedCartItem.getCartItemId())) {
                                    currentItems.set(i, modifiedCartItem);
                                    break;
                                }
                            }
                            break;
                        case REMOVED:
                            Log.d(TAG, "Cart item removed: " + dc.getDocument().getData());
                            CartItem removedCartItem = dc.getDocument().toObject(CartItem.class);
                            currentItems.removeIf(cartItem -> cartItem.getCartItemId().equals(removedCartItem.getCartItemId()));
                            break;
                    }
                }
                cartItems.setValue(currentItems);
            } else {
                Log.d(TAG, "Current data: null");
            }
        });
    }
}
