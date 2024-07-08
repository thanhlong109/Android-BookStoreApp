package com.group2.bookstoreproject.ui.common.orderDetails;

import android.nfc.Tag;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.group2.bookstoreproject.base.BaseViewModel;
import com.group2.bookstoreproject.data.model.Book;
import com.group2.bookstoreproject.data.model.Order;
import com.group2.bookstoreproject.data.model.OrderItem;
import com.group2.bookstoreproject.data.model.User;
import com.group2.bookstoreproject.data.repository.AuthRepository;
import com.group2.bookstoreproject.data.repository.BookRepository;
import com.group2.bookstoreproject.data.repository.OrderItemsRepository;
import com.group2.bookstoreproject.data.repository.OrderRepository;
import com.group2.bookstoreproject.data.repositoryImpl.AuthRepositoryImpl;
import com.group2.bookstoreproject.data.repositoryImpl.BookRepositoryImpl;
import com.group2.bookstoreproject.data.repositoryImpl.OrderItemsRepositoryImpl;
import com.group2.bookstoreproject.data.repositoryImpl.OrderRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailsViewModel extends BaseViewModel {
    private final OrderRepository orderRepository;
    private final MutableLiveData<Order> order = new MutableLiveData<>();
    private final AuthRepository authRepository;
    private final MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();
    private final OrderItemsRepository orderItemsRepository;
    private final MutableLiveData<List<OrderItem>>orderItemMutableLiveData = new MutableLiveData<>();
    private final BookRepository bookRepository;
    private final MutableLiveData<List<Book>> bookMutableLiveData = new MutableLiveData<>();

    public OrderDetailsViewModel() {
        this.orderRepository = new OrderRepositoryImpl(); // Khởi tạo repository
        this.authRepository = new AuthRepositoryImpl();
        this.orderItemsRepository = new OrderItemsRepositoryImpl();
        this.bookRepository = new BookRepositoryImpl();
    }

    public LiveData<Order> getOrder() {
        return order;
    }
    public LiveData<List<OrderItem>> getOrderItems(){
        return orderItemMutableLiveData;
    }
    public LiveData<User> getUser(){
        return userMutableLiveData;
    }
    public LiveData<List<Book>> getBooks(){
        return bookMutableLiveData;
    }
    public void getOrderDetails(String orderId) {
        orderRepository.getById(orderId, task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                Order loadedOrder = task.getResult().toObject(Order.class);
                if (loadedOrder != null) {
                    order.setValue(loadedOrder);
                } else {
                    // Xử lý khi không có dữ liệu đơn hàng
                    order.setValue(null);
                }
            } else {
                // Xử lý khi không thể lấy dữ liệu đơn hàng
                order.setValue(null);
            }
        });
    }
    public  void getUserbyUserId(String userId){
        authRepository.getById(userId, task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                User loadedUser = task.getResult().toObject(User.class);
                if (loadedUser != null) {
                    userMutableLiveData.setValue(loadedUser);
                } else {
                    // Xử lý khi không có dữ liệu đơn hàng
                    userMutableLiveData.setValue(null);
                }
            } else {
                // Xử lý khi không thể lấy dữ liệu đơn hàng
                userMutableLiveData.setValue(null);
            }
        });
    }
    public void addOrder(OrderItem order) {
        orderItemsRepository.upsert(order.getOrderItemId(), order, task -> {
            if (task.isSuccessful()) {

            } else {

            }
        });
    }

    public void getOrderDetailByOrderId(String orderId) {
        orderItemsRepository.getOrdersDetailByOrderId(orderId, task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                List<OrderItem> orderItemList = task.getResult().toObjects(OrderItem.class);
                orderItemMutableLiveData.setValue(orderItemList);

                // Lấy danh sách books sau khi có danh sách order items
                List<String> bookIds = new ArrayList<>();
                for (OrderItem orderItem : orderItemList) {
                    bookIds.add(orderItem.getBookId());
                }
                getBooksByBookIds(bookIds);
            } else {
                // Xử lý khi không thể lấy danh sách order items
                orderItemMutableLiveData.setValue(null);
            }
        });
    }

    private void getBooksByBookIds(List<String> bookIds) {
        bookRepository.getBooksByIds(bookIds, task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                List<Book> books = task.getResult().toObjects(Book.class);
                bookMutableLiveData.setValue(books);
            } else {
                // Xử lý khi không thể lấy danh sách books
                bookMutableLiveData.setValue(null);
            }
        });
    }


}
