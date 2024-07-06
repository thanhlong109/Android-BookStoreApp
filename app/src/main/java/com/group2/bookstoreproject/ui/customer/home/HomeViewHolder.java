package com.group2.bookstoreproject.ui.customer.home;

import android.content.Context;
import android.util.Log;

import com.group2.bookstoreproject.base.BaseViewModel;
import com.group2.bookstoreproject.data.model.CartItem;
import com.group2.bookstoreproject.data.model.FCMRequest;
import com.group2.bookstoreproject.data.repository.CartItemRepository;
import com.group2.bookstoreproject.data.repository.NotificationRepository;
import com.group2.bookstoreproject.data.repositoryImpl.CartItemRepositoryImpl;
import com.group2.bookstoreproject.data.repositoryImpl.NotificationRepositoryImpl;

public class HomeViewHolder extends BaseViewModel {
    private final String TAG = "HomeViewHolder";
    private final NotificationRepository notificationRepository = new NotificationRepositoryImpl();
    private final CartItemRepository cartItemRepository = new CartItemRepositoryImpl();
    public void showNotificationTotalCardItems(Context context) {
        cartItemRepository.getCartItems(sessionManager.getLoggedInUser().getUserId(), task -> {
            if (task.isSuccessful()) {
                int size = task.getResult().size();
                String title = size > 0 ?"Thanh toán ngay":"Mua sách ngay";
                String des = size > 0 ? "Bạn đang có "+size+" cuốn sách trong giỏ hàng!": "Giỏ hàng bạn đang trống!";
                sendNotification(context,title, des );
            } else {
                Log.e(TAG, "Error loading cart items", task.getException());
            }
        });
    }
    public void sendNotification(Context context, String title, String body){
        FCMRequest.Notification notification = new FCMRequest.Notification(title, body);
        FCMRequest.Message message = new FCMRequest.Message(sessionManager.getLoggedInUser().getDeviceToken(), notification);
        FCMRequest fcmRequest = new FCMRequest(message);
        notificationRepository.sendNotification(fcmRequest, context);
    }
}
