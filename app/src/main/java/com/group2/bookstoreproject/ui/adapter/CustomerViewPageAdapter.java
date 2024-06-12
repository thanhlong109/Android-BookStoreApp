package com.group2.bookstoreproject.ui.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.group2.bookstoreproject.ui.common.chat.ChatFragment;
import com.group2.bookstoreproject.ui.customer.cart.CartFragment;
import com.group2.bookstoreproject.ui.customer.home.HomeFragment;
import com.group2.bookstoreproject.ui.customer.profile.ProfileFragment;

public class CustomerViewPageAdapter extends FragmentStateAdapter{

    public CustomerViewPageAdapter(FragmentManager fragmentManager, Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new HomeFragment();
            case 1: return new ChatFragment();
            case 2: return  new CartFragment();
            case 3 : return  new ProfileFragment();
        }

        return new HomeFragment();
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
