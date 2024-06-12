package com.group2.bookstoreproject.ui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.group2.bookstoreproject.R;
import com.group2.bookstoreproject.base.BaseActivity;
import com.group2.bookstoreproject.databinding.ActivityAuthBinding;

public class AuthActivity extends BaseActivity<ActivityAuthBinding> {

    @NonNull
    @Override
    protected ActivityAuthBinding inflateBinding(LayoutInflater layoutInflater) {
        return ActivityAuthBinding.inflate(layoutInflater);
    }

    @Override
    protected void setUpUI() {

    }


}