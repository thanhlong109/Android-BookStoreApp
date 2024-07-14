package com.group2.bookstoreproject.ui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.group2.bookstoreproject.R;
import com.group2.bookstoreproject.base.BaseActivity;
import com.group2.bookstoreproject.base.common.Constants;
import com.group2.bookstoreproject.databinding.ActivityAuthBinding;
import com.group2.bookstoreproject.databinding.ActivityShipperBinding;

import java.util.HashMap;

public class ShipperActivity extends BaseActivity<ActivityShipperBinding> {

    @NonNull
    @Override
    protected ActivityShipperBinding inflateBinding(LayoutInflater layoutInflater) {
        return ActivityShipperBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void setUpUI() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_shipper);
        NavController navController = navHostFragment.getNavController();
        if (navHostFragment != null) {
            HashMap<Integer, MenuItem> menuItemHashMap = new HashMap<>();
            Menu menu = binding.shipperBottomNavigation.getMenu();
            for(int i = 0; i<menu.size(); i++){
                MenuItem menuItem = menu.getItem(i);
                menuItemHashMap.put(menuItem.getItemId(),menuItem);
            }
            navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
                @Override
                public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                    int selectedId = Constants.BottomNav.getNavActive(navDestination.getId(), Constants.BottomNav.SHIPPER_NAV);
                    menuItemHashMap.get(selectedId).setChecked(true);
                }
            });
            NavigationUI.setupWithNavController(binding.shipperBottomNavigation, navController);
        }
    }
}