package com.group2.bookstoreproject.data.repository;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;
import com.group2.bookstoreproject.data.model.CartItem;
import com.google.firebase.firestore.EventListener;;

import java.util.List;
import java.util.function.Consumer;

public interface CartItemRepository extends BaseRepository<CartItem> {
    void getCartItems(String accountId, OnCompleteListener<QuerySnapshot> onCompleteListener);
    void updateQuantity(CartItem item, int quantity, OnCompleteListener<Void> onCompleteListener);
    void removeItem(CartItem item, OnCompleteListener<Void> onCompleteListener);
    ListenerRegistration listenToCartItems(String accountId, EventListener<QuerySnapshot> eventListener);
    ListenerRegistration listenToAllCartItems(EventListener<QuerySnapshot> listener);
    void getCartItemByBookAndAccountId(String accountId, String bookId, OnCompleteListener<QuerySnapshot> onCompleteListener);

}