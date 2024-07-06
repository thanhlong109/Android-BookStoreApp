package com.group2.bookstoreproject.data.repository;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.QuerySnapshot;
import com.group2.bookstoreproject.data.model.Order;

public interface OrderRepository extends BaseRepository<Order>{
    void getOrdersByUserId(String userId, OnCompleteListener<QuerySnapshot> onCompleteListener);
    void updateOrderStatus(String orderId, int newStatus, OnCompleteListener<Void> onCompleteListener);
}
