package com.group2.bookstoreproject.data.repositoryImpl;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.group2.bookstoreproject.data.model.OrderItem;
import com.group2.bookstoreproject.data.repository.OrderItemsRepository;

public class OrderItemsRepositoryImpl extends BaseRepositoryImpl<OrderItem> implements OrderItemsRepository {
    private static final String COLLECTION_PATH = "orderItems";
    @Override
    protected String getCollectionPath() {
        return COLLECTION_PATH;
    }
    public void getOrdersDetailByOrderId(String orderId, OnCompleteListener<QuerySnapshot> onCompleteListener) {
        Query query = getCollection(getCollectionPath()).whereEqualTo("orderId", orderId);
        query(query, onCompleteListener);
    }
}
