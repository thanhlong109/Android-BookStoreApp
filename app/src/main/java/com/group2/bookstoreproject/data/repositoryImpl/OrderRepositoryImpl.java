package com.group2.bookstoreproject.data.repositoryImpl;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.group2.bookstoreproject.data.model.Order;
import com.group2.bookstoreproject.data.repository.OrderRepository;

public class OrderRepositoryImpl extends BaseRepositoryImpl<Order> implements OrderRepository {

    private static final String COLLECTION_PATH = "orders";
    @Override
    protected String getCollectionPath() {
        return COLLECTION_PATH;
    }
    public void getOrdersByUserId(String userId, OnCompleteListener<QuerySnapshot> onCompleteListener) {
        Query query = getCollection(getCollectionPath()).whereEqualTo("userId", userId);
        query(query, onCompleteListener);
    }
    public void updateOrderStatus(String orderId, int newStatus, OnCompleteListener<Void> onCompleteListener) {
        DocumentReference orderRef = getCollection(getCollectionPath()).document(orderId);
        orderRef.update("orderStatus", newStatus)
                .addOnCompleteListener(onCompleteListener);
    }
}
