package com.group2.bookstoreproject.ui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.firebase.messaging.FirebaseMessaging;
import com.group2.bookstoreproject.R;
import com.group2.bookstoreproject.base.BaseActivity;
import com.group2.bookstoreproject.base.common.Constants;
import com.group2.bookstoreproject.databinding.ActivityAdminBinding;
import com.group2.bookstoreproject.util.session.SessionManager;

import java.util.HashMap;

public class AdminActivity extends BaseActivity<ActivityAdminBinding> {

    @NonNull
    @Override
    protected ActivityAdminBinding inflateBinding(LayoutInflater layoutInflater) {
        return ActivityAdminBinding.inflate(layoutInflater);
    }

    @Override
    protected void setUpUI() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_admin);
        NavController navController = navHostFragment.getNavController();
        if (navHostFragment != null) {
            HashMap<Integer, MenuItem> menuItemHashMap = new HashMap<>();
            Menu menu = binding.adminBottomNavigation.getMenu();
            for(int i = 0; i<menu.size(); i++){
                MenuItem menuItem = menu.getItem(i);
                menuItemHashMap.put(menuItem.getItemId(),menuItem);
            }
            navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
                @Override
                public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                    int selectedId = Constants.BottomNav.getNavActive(navDestination.getId(), Constants.BottomNav.ADMIN_NAV);
                    MenuItem menuItem =  menuItemHashMap.get(selectedId);
                    if(menuItem!=null){
                        menuItem.setChecked(true);
                    }
                }
            });
            NavigationUI.setupWithNavController(binding.adminBottomNavigation, navController);
        }
        //listen to customer id notification
        FirebaseMessaging.getInstance().subscribeToTopic(SessionManager.getInstance().getLoggedInUser().getDeviceToken());
    }




}