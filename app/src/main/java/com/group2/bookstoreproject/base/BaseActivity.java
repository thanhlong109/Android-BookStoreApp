package com.group2.bookstoreproject.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import com.group2.bookstoreproject.base.dialog.ConfirmDialog;
import com.group2.bookstoreproject.base.dialog.ErrorDialog;
import com.group2.bookstoreproject.base.dialog.LoadingDialog;
import com.group2.bookstoreproject.base.dialog.NotifyDialog;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.WindowInsetsCompat.Type;
import androidx.viewbinding.ViewBinding;

public abstract class BaseActivity<T extends ViewBinding> extends AppCompatActivity {

    private LoadingDialog loadingDialog;
    protected T binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = inflateBinding(getLayoutInflater());
        setContentView(binding.getRoot());
        setUpUI();
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @NonNull
    protected abstract T inflateBinding(LayoutInflater layoutInflater);

    protected abstract void setUpUI();

    public void showLoading(boolean isShow) {
        if (isShow) {
            showLoading();
        } else {
            hideLoading();
        }
    }


    public void showLoading() {
        if(loadingDialog == null)
        loadingDialog = new LoadingDialog(this);
        loadingDialog.show();

    }

    public void hideLoading() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    public void showErrorDialog(@NonNull String message) {
        ErrorDialog errorDialog = new ErrorDialog(this, message);
        errorDialog.show();
        if (errorDialog.getWindow() != null) {
            errorDialog.getWindow().setGravity(Gravity.CENTER);
            errorDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    public void showNotifyDialog(int titleResourceId, int messageResourceId, int textButtonResourceId) {
        String title = getString(titleResourceId);
        String message = getString(messageResourceId);
        String textButton = textButtonResourceId == -1 ? null : getString(textButtonResourceId);
        showNotifyDialog(title, message, textButton);
    }

    public void showNotifyDialog(int titleResourceId, int messageResourceId) {
        showNotifyDialog(titleResourceId, messageResourceId, -1);
    }

    public void showNotifyDialog(@NonNull String message, @Nullable String title, @Nullable String textButton) {
        NotifyDialog notifyDialog = new NotifyDialog(this, title, message, textButton);
        notifyDialog.show();
        if (notifyDialog.getWindow() != null) {
            notifyDialog.getWindow().setGravity(Gravity.CENTER);
            notifyDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    public void showNotifyDialog(@NonNull String message, @Nullable String title) {
        showNotifyDialog(message, title, null);
    }

    public void showConfirmDialog(@Nullable String title, @NonNull String message, @Nullable String positiveButtonTitle, @Nullable String negativeButtonTitle, @NonNull ConfirmDialog.ConfirmCallback callback) {
        ConfirmDialog confirmDialog = new ConfirmDialog(this, callback, title, message, positiveButtonTitle, negativeButtonTitle);
        confirmDialog.show();
        if (confirmDialog.getWindow() != null) {
            confirmDialog.getWindow().setGravity(Gravity.CENTER);
            confirmDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    public void goToActivity(Class classx, boolean isFinish){
        Intent intent = new Intent(this,classx);
        startActivity(intent);
        if(isFinish){
            finish();
        }
    }

    public void goToActivity(Class classx){
        goToActivity(classx, false);
    }
}