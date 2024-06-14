package com.group2.bookstoreproject.ui.admin.bookManager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.group2.bookstoreproject.R;
import com.group2.bookstoreproject.base.BaseFragment;
import com.group2.bookstoreproject.databinding.FragmentBookManagerBinding;

public class BookManagerFragment extends BaseFragment<FragmentBookManagerBinding, BookManagerViewModel> {


    @NonNull
    @Override
    protected FragmentBookManagerBinding inflateBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, boolean attachToParent) {
        return FragmentBookManagerBinding.inflate(inflater, container, attachToParent);
    }

    @NonNull
    @Override
    protected Class<BookManagerViewModel> getViewModelClass() {
        return BookManagerViewModel.class;
    }
}