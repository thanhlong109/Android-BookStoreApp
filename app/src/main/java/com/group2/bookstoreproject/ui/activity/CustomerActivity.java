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
import androidx.viewbinding.ViewBinding;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.group2.bookstoreproject.R;
import com.group2.bookstoreproject.base.BaseActivity;
import com.group2.bookstoreproject.base.common.Constants;
import com.group2.bookstoreproject.databinding.ActivityCustomerBinding;
import com.group2.bookstoreproject.ui.adapter.CustomerViewPageAdapter;
import com.group2.bookstoreproject.util.session.SessionManager;

import java.util.HashMap;

public class CustomerActivity extends BaseActivity<ActivityCustomerBinding> {
    private static Runnable runnable;

    public static void setCallBackWhenResume(Runnable runnable){
        CustomerActivity.runnable = runnable;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(runnable!=null){
            runnable.run();
            runnable = null;
        }
    }

    @NonNull
    @Override
    protected ActivityCustomerBinding inflateBinding(LayoutInflater layoutInflater) {
        return ActivityCustomerBinding.inflate(layoutInflater);
    }

    @Override
    protected void setUpUI() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_cus);
        NavController navController = navHostFragment.getNavController();
        if (navHostFragment != null) {
            HashMap<Integer,MenuItem> menuItemHashMap = new HashMap<>();
            Menu menu = binding.cusBottomNavigation.getMenu();
            for(int i = 0; i<menu.size(); i++){
                MenuItem menuItem = menu.getItem(i);
                menuItemHashMap.put(menuItem.getItemId(),menuItem);
            }
            navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
                @Override
                public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                    int selectedId = Constants.BottomNav.getNavActive(navDestination.getId(), Constants.BottomNav.CUS_NAV);
                    menuItemHashMap.get(selectedId).setChecked(true);
                }
            });
            NavigationUI.setupWithNavController(binding.cusBottomNavigation, navController);
        }

        //listen to customer id notification
        FirebaseMessaging.getInstance().subscribeToTopic(SessionManager.getInstance().getLoggedInUser().getDeviceToken());
        //listen to global id notification
        FirebaseMessaging.getInstance().subscribeToTopic(Constants.GLOBAL_NOTIFICATION_ID);
    }
}