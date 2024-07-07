package com.group2.bookstoreproject.data.repository;

import android.content.Context;

import com.group2.bookstoreproject.data.model.FCMNotification;
import com.group2.bookstoreproject.data.model.FCMRequest;
import com.group2.bookstoreproject.data.model.Notification;

public interface NotificationRepository extends BaseRepository<Notification>{
    void sendNotification(FCMRequest notification, Context context);
}
