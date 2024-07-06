package com.group2.bookstoreproject.ui.customer.payment;

import android.util.Log;
import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.group2.bookstoreproject.base.BaseItemViewHolder;
import com.group2.bookstoreproject.data.model.Book;
import com.group2.bookstoreproject.data.model.CartItem;
import com.group2.bookstoreproject.data.repository.BookRepository;
import com.group2.bookstoreproject.data.repositoryImpl.BookRepositoryImpl;
import com.group2.bookstoreproject.databinding.RowItemsPaymentBinding;

public class CartItemPaymentViewHolder extends BaseItemViewHolder<CartItem, RowItemsPaymentBinding> {
    private final BookRepository bookRepository;
    private static final String TAG = "CartItemViewHolder";

    public CartItemPaymentViewHolder(@NonNull RowItemsPaymentBinding binding) {
        super(binding);
        this.bookRepository = new BookRepositoryImpl();
    }

    @Override
    public void bind(CartItem item) {
        bookRepository.getById(item.getBookId(), task -> {
            if (task.isSuccessful()) {
                Book book = task.getResult().toObject(Book.class);
                if (book != null) {
                    binding.textViewQuantity.setText("x "+String.valueOf(item.getQuantity()));
                    binding.textViewBookPrice.setText(String.valueOf(book.getPrice()*item.getQuantity()));
                    binding.textViewBookTitle.setText(book.getTitle());
                    binding.textViewBookAuthor.setText(book.getAuthor());

                    Glide.with(binding.imageViewBookCover.getContext())
                            .load(book.getBookImg())
                            .into(binding.imageViewBookCover);
                } else {
                    Log.e(TAG, "Book data is null");
                }
            } else {
                Log.e(TAG, "Error getting book by id", task.getException());
            }
        });
    }
}
