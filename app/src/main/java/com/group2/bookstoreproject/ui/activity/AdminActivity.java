package com.group2.bookstoreproject.ui.activity;

import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.firebase.messaging.FirebaseMessaging;
import com.group2.bookstoreproject.R;
import com.group2.bookstoreproject.base.BaseActivity;
import com.group2.bookstoreproject.databinding.ActivityAdminBinding;
import com.group2.bookstoreproject.util.session.SessionManager;

public class AdminActivity extends BaseActivity<ActivityAdminBinding> {

    @NonNull
    @Override
    protected ActivityAdminBinding inflateBinding(LayoutInflater layoutInflater) {
        return ActivityAdminBinding.inflate(layoutInflater);
    }

    @Override
    protected void setUpUI() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_admin);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
            NavigationUI.setupWithNavController(binding.adminBottomNavigation, navController);
        }
        //listen to customer id notification
        FirebaseMessaging.getInstance().subscribeToTopic(SessionManager.getInstance().getLoggedInUser().getDeviceToken());
    }




}