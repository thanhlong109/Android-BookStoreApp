package com.group2.bookstoreproject.ui.customer.payment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.group2.bookstoreproject.base.BaseViewModel;
import com.group2.bookstoreproject.data.model.CartItem;
import com.group2.bookstoreproject.data.model.User;
import com.group2.bookstoreproject.data.repository.CartItemRepository;
import com.group2.bookstoreproject.data.repository.ProfileRepository;
import com.group2.bookstoreproject.data.repositoryImpl.CartItemRepositoryImpl;
import com.group2.bookstoreproject.data.repositoryImpl.ProfileRepositoryImpl;

import java.util.List;

public class PaymentViewModel extends BaseViewModel {
    private final CartItemRepository cartRepository;
    private final MutableLiveData<List<CartItem>> cartItemsLiveData = new MutableLiveData<>();
    private final MutableLiveData<User> userLiveData = new MutableLiveData<>();
    private final ProfileRepository profileRepository;

    public PaymentViewModel() {
        cartRepository = new CartItemRepositoryImpl();
        profileRepository = new ProfileRepositoryImpl();
    }

    public LiveData<List<CartItem>> getCartItemsLiveData() {
        return cartItemsLiveData;
    }

    public LiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public void loadCartItems(String accountId) {
        cartRepository.getCartItems(accountId, task -> {
            if (task.isSuccessful()) {
                List<CartItem> cartItems = task.getResult().toObjects(CartItem.class);
                cartItemsLiveData.setValue(cartItems);
            } else {
                // handle error here if needed
            }
        });
    }

    public void loadUser(String accountId) {
        profileRepository.getById(accountId, task -> {
            if (task.isSuccessful()) {
                User user = task.getResult().toObject(User.class);
                userLiveData.setValue(user);
            } else {
                // handle error here if needed
            }
        });
    }
}
