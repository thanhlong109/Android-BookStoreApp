package com.group2.bookstoreproject.ui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.group2.bookstoreproject.R;
import com.group2.bookstoreproject.base.BaseActivity;
import com.group2.bookstoreproject.databinding.ActivityAuthBinding;
import com.group2.bookstoreproject.databinding.ActivityShipperBinding;

public class ShipperActivity extends BaseActivity<ActivityShipperBinding> {

    @NonNull
    @Override
    protected ActivityShipperBinding inflateBinding(LayoutInflater layoutInflater) {
        return ActivityShipperBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void setUpUI() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_shipper);
        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            NavigationUI.setupWithNavController(binding.shipperBottomNavigation, navController);
        }
    }
}