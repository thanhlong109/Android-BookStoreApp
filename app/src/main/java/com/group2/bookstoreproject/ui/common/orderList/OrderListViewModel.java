package com.group2.bookstoreproject.ui.common.orderList;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.group2.bookstoreproject.base.BaseViewModel;
import com.group2.bookstoreproject.data.model.Book;
import com.group2.bookstoreproject.data.model.Order;
import com.group2.bookstoreproject.data.repository.AuthRepository;
import com.group2.bookstoreproject.data.repository.OrderRepository;
import com.group2.bookstoreproject.data.repositoryImpl.AuthRepositoryImpl;
import com.group2.bookstoreproject.data.repositoryImpl.OrderRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.callback.Callback;

public class OrderListViewModel extends BaseViewModel {

    private final OrderRepository orderRepository;
    private List<Order> AllListOrder = new ArrayList<>();
    private final MutableLiveData<List<Order>> ordersLiveData = new MutableLiveData<>();

    private static final String TAG = "OrderListViewModel";

    public LiveData<List<Order>> getOrders(){
        return ordersLiveData;
    }
    public OrderListViewModel() {
        orderRepository = new OrderRepositoryImpl();

    }

    public void addOrder(Order order) {
        orderRepository.upsert(order.getOrderId(), order, task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, "Order added successfully");
            } else {
                Log.e(TAG, "Error adding Order", task.getException());
            }
        });
    }

    public void fetchOrders(){
        setLoading(true);
        orderRepository.getAll(task -> {
            if (task.isSuccessful()) {
                AllListOrder = task.getResult().toObjects(Order.class);
                ordersLiveData.setValue(new ArrayList<>(AllListOrder));
            } else {
                Log.e(TAG, "Error fetching books", task.getException());
            }
            setLoading(false);
        });
    }

    public void filterOrdersByStatus(int status) {
        List<Order> filteredOrders = new ArrayList<>();
        for (Order order : AllListOrder) {
            if (order.getOrderStatus() == status) {
                filteredOrders.add(order);
            }
        }
        ordersLiveData.setValue(filteredOrders);
    }
    public void filterOrdersByUserId(String userID) {
        List<Order> filteredOrders = new ArrayList<>();
        for (Order order : AllListOrder) {
            if (order.getUserId() == userID) {
                filteredOrders.add(order);
            }
        }
        ordersLiveData.setValue(filteredOrders);
    }
    public void fetchOrdersByUserId(String userId) {
        setLoading(true);
        orderRepository.getOrdersByUserId(userId, task -> {
            if (task.isSuccessful()) {
                AllListOrder = task.getResult().toObjects(Order.class);
                ordersLiveData.setValue(new ArrayList<>(AllListOrder));
            } else {
                Log.e(TAG, "Error fetching orders by userId", task.getException());
            }
            setLoading(false);
        });
    }
    public void updateOrderStatus(String orderId, int newStatus) {
        setLoading(true);
        orderRepository.updateOrderStatus(orderId, newStatus, task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, "Order status updated successfully");
                // Refresh the list after updating status
                fetchOrders(); // Or fetchOrdersByUserId(accountId) based on role
            } else {
                Log.e(TAG, "Error updating order status", task.getException());
            }
            setLoading(false);
        });
    }
}
