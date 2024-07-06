package com.group2.bookstoreproject.ui.admin.upsertBook;

import android.content.Context;

import com.group2.bookstoreproject.base.BaseViewModel;
import com.group2.bookstoreproject.data.model.FCMRequest;
import com.group2.bookstoreproject.data.repository.NotificationRepository;
import com.group2.bookstoreproject.data.repositoryImpl.NotificationRepositoryImpl;

public class UpsertViewModel extends BaseViewModel {
    private final NotificationRepository notificationRepository = new NotificationRepositoryImpl();

    public  void sendNotificationToCustomer(Context context, String title, String message){
        FCMRequest.Notification notification = new FCMRequest.Notification(title, message);
        FCMRequest.Message body = new FCMRequest.Message(sessionManager.getLoggedInUser().getDeviceToken(), notification);
        FCMRequest fcmRequest = new FCMRequest(body);
        notificationRepository.sendNotification(fcmRequest,context);
    }

}
