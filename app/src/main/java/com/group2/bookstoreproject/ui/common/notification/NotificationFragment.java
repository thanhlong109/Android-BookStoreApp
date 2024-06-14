package com.group2.bookstoreproject.ui.common.notification;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.group2.bookstoreproject.R;
import com.group2.bookstoreproject.base.BaseFragment;
import com.group2.bookstoreproject.databinding.FragmentNotificationBinding;


public class NotificationFragment extends BaseFragment<FragmentNotificationBinding, NotificationViewModel> {


    @NonNull
    @Override
    protected FragmentNotificationBinding inflateBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, boolean attachToParent) {
        return FragmentNotificationBinding.inflate(inflater, container, attachToParent);
    }

    @NonNull
    @Override
    protected Class<NotificationViewModel> getViewModelClass() {
        return NotificationViewModel.class;
    }
}