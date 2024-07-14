package com.group2.bookstoreproject.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewbinding.ViewBinding;
import com.group2.bookstoreproject.R;

public abstract class BaseFragment<B extends ViewBinding, VM extends BaseViewModel> extends Fragment {

    protected B binding;
    protected VM viewModel;

    @NonNull
    protected abstract B inflateBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, boolean attachToParent);

    @NonNull
    protected abstract Class<VM> getViewModelClass();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = inflateBinding(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(getViewModelClass());
        observeViewModel();
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    protected void observeViewModel() {
        viewModel.getIsLoading().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                showLoading(isLoading);
            }
        });

        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                if (errorMessage != null) {
                    showErrorMessage(errorMessage);
                }
            }
        });
    }

    protected void navigateBack(){
        if(getActivity() instanceof BaseActivity){
            BaseActivity baseActivity = (BaseActivity) getActivity();
            baseActivity.getNavController().popBackStack();
        }
    }

    protected void navigateBack(int destinationId, boolean inclusive){
        if(getActivity() instanceof BaseActivity){
            BaseActivity baseActivity = (BaseActivity) getActivity();
            baseActivity.getNavController().popBackStack(destinationId, inclusive);
        }
    }

    protected void navigateToPage(int actionId) {
        NavHostFragment.findNavController(this).navigate(actionId);
    }

    protected void navigateToPage(int actionId, Bundle bundle) {
        NavHostFragment.findNavController(this).navigate(actionId, bundle);
    }

    protected void navigateToPage(NavDirections direction) {
        NavHostFragment.findNavController(this).navigate(direction);
    }

    protected void showLoading(boolean isShow) {
        BaseActivity activity = (BaseActivity) requireActivity();
        activity.showLoading(isShow);
    }

    protected void hideLoading() {
        BaseActivity activity = (BaseActivity) requireActivity();
        activity.hideLoading();
    }

    protected void showErrorMessage(int messageId) {
        String message = getString(messageId);
        showErrorMessage(message);
    }

    protected void showErrorMessage(String message) {
        BaseActivity activity = (BaseActivity) requireActivity();
        activity.showErrorDialog(message);
    }

    protected void showNotify(String title, String message) {
        BaseActivity activity = (BaseActivity) requireActivity();
        activity.showNotifyDialog(title != null ? title : getDefaultNotifyTitle(), message);
    }

    protected void showToast(String message){
        Toast.makeText(getContext(),message, Toast.LENGTH_SHORT).show();
    }

    private String getDefaultNotifyTitle() {
        return getString(R.string.default_notify_title);
    }

    // Hiển thị thông báo bằng ID tiêu đề và ID tin nhắn
    protected void showNotify(int titleId, int messageId) {
        BaseActivity activity = (BaseActivity) requireActivity();
        activity.showNotifyDialog(titleId, messageId);
    }

    // Hiển thị hoặc ẩn loading more
    protected void showLoadingMore(boolean isShow) {
        // Implement your logic for loading more here
    }

    protected void goToActivity(Class classx, boolean isFinish){
        BaseActivity activity = (BaseActivity) requireActivity();
        activity.goToActivity(classx,isFinish);
    }

    protected void goToActivity(Class classx){
        goToActivity(classx, false);
    }
}