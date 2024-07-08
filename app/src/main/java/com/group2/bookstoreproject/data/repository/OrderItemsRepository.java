package com.group2.bookstoreproject.data.repository;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.QuerySnapshot;
import com.group2.bookstoreproject.data.model.OrderItem;

public interface OrderItemsRepository extends BaseRepository<OrderItem>{
    void getOrdersDetailByOrderId(String orderId, OnCompleteListener<QuerySnapshot> onCompleteListener);
}
