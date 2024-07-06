package com.group2.bookstoreproject.ui.customer.home;

import android.content.Context;

import com.group2.bookstoreproject.base.BaseViewModel;
import com.group2.bookstoreproject.data.model.FCMRequest;
import com.group2.bookstoreproject.data.repository.NotificationRepository;
import com.group2.bookstoreproject.data.repositoryImpl.NotificationRepositoryImpl;

public class HomeViewHolder extends BaseViewModel {

    private final NotificationRepository notificationRepository = new NotificationRepositoryImpl();
    public void sendNotification(Context context){
        FCMRequest.Notification notification = new FCMRequest.Notification("Breaking News", "New news story available.");
        FCMRequest.Message message = new FCMRequest.Message(sessionManager.getLoggedInUser().getDeviceToken(), notification);
        FCMRequest fcmRequest = new FCMRequest(message);
        notificationRepository.sendNotification(fcmRequest, context);
    }
}
