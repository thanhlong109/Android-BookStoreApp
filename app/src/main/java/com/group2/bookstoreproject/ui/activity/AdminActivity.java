package com.group2.bookstoreproject.ui.activity;

import android.view.LayoutInflater;

import androidx.annotation.NonNull;

import com.group2.bookstoreproject.base.BaseActivity;
import com.group2.bookstoreproject.databinding.ActivityAdminBinding;

public class AdminActivity extends BaseActivity<ActivityAdminBinding> {

    @NonNull
    @Override
    protected ActivityAdminBinding inflateBinding(LayoutInflater layoutInflater) {
        return ActivityAdminBinding.inflate(layoutInflater);
    }

    @Override
    protected void setUpUI() {

    }


}