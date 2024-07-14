package com.group2.bookstoreproject.ui.admin.bookManager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.group2.bookstoreproject.R;
import com.group2.bookstoreproject.base.BaseFragment;
import com.group2.bookstoreproject.base.common.Constants;
import com.group2.bookstoreproject.data.model.Book;
import com.group2.bookstoreproject.databinding.FragmentBookManagerBinding;

import java.util.List;

public class BookManagerFragment extends BaseFragment<FragmentBookManagerBinding, BookManagerViewModel> {
    private BookItemAdapter adapter;

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

    @Override
    protected void observeViewModel() {
        super.observeViewModel();
        viewModel.getBooks().observe(getViewLifecycleOwner(), books -> {
            if (books == null || books.isEmpty()) {
                showEmptyBookLayout();
            } else {
                showBooks(books);
            }
        });
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpRecyclerView();
        viewModel.loadBooks();

        binding.fabAdd.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.MODE_KEY,Constants.CREATE_MODE);
            navigateToPage(R.id.action_bookManagerFragment_to_upsertBookFragment,bundle);
        });
    }

    private void setUpRecyclerView() {
        RecyclerView recyclerView = binding.recyclerViewBook;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BookItemAdapter(viewModel);

        //send to book details
        adapter.setItemOnClickListener((book) ->{
            Bundle bundle = new Bundle();
            bundle.putSerializable("book", book);
            navigateToPage(R.id.action_bookManagerFragment_to_bookDetailsFragment,bundle);

        });

        recyclerView.setAdapter(adapter);
    }

    private void showEmptyBookLayout() {
        binding.recyclerViewBook.setVisibility(View.GONE);
        binding.emptyBookLayout.getRoot().setVisibility(View.VISIBLE);
    }

    private void showBooks(List<Book> books) {
        binding.recyclerViewBook.setVisibility(View.VISIBLE);
        binding.emptyBookLayout.getRoot().setVisibility(View.GONE);
        adapter.submitList(books);
    }
}