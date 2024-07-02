package com.group2.bookstoreproject.ui.admin.bookManager;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.group2.bookstoreproject.base.BaseItemViewHolder;
import com.group2.bookstoreproject.data.model.Book;
import com.group2.bookstoreproject.databinding.RowItemBookBinding;

public class BookManagerViewHolder extends BaseItemViewHolder<Book, RowItemBookBinding> {

    private final BookManagerViewModel viewModel;
    public BookManagerViewHolder(@NonNull RowItemBookBinding binding, BookManagerViewModel viewModel) {
        super(binding);
        this.viewModel = viewModel;
    }

    @Override
    public void bind(Book item) {
        binding.bookTitle.setText(item.getTitle());
        binding.bookAuthor.setText(item.getAuthor());
        binding.bookPrice.setText(String.valueOf(item.getPrice()));
        Glide.with(binding.bookCover.getContext())
                .load(item.getBookImg())
                .into(binding.bookCover);

        binding.btnDelete.setOnClickListener(v -> viewModel.deleteBook(item.getBookId()));
        binding.btnUpdate.setOnClickListener(v -> {
            // Logic to show update dialog
        });
        binding.getRoot().setOnClickListener(v -> {
            // Logic to show book details
        });
    }
}
