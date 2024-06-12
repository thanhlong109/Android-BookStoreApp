package com.group2.bookstoreproject.ui.common.bookDetails;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.group2.bookstoreproject.R;
import com.group2.bookstoreproject.base.BaseFragment;
import com.group2.bookstoreproject.databinding.FragmentBookDetailsBinding;
import com.group2.bookstoreproject.databinding.FragmentChatBinding;


public class BookDetailsFragment extends BaseFragment<FragmentBookDetailsBinding, BookDetailsViewModel> {


    @NonNull
    @Override
    protected FragmentBookDetailsBinding inflateBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, boolean attachToParent) {
        return FragmentBookDetailsBinding.inflate(inflater, container,attachToParent);
    }

    @NonNull
    @Override
    protected Class<BookDetailsViewModel> getViewModelClass() {
        return BookDetailsViewModel.class;
    }
}