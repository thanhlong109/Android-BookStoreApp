package com.group2.bookstoreproject.ui.customer.cart;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.group2.bookstoreproject.base.BaseItemViewHolder;
import com.group2.bookstoreproject.data.model.Book;
import com.group2.bookstoreproject.data.model.CartItem;
import com.group2.bookstoreproject.data.model.Category;
import com.group2.bookstoreproject.data.repository.BookRepository;
import com.group2.bookstoreproject.data.repository.CategoryRepository;
import com.group2.bookstoreproject.data.repositoryImpl.BookRepositoryImpl;
import com.group2.bookstoreproject.data.repositoryImpl.CategoryRepositoryImpl;
import com.group2.bookstoreproject.databinding.RowItemCartBinding;

public class CartItemViewHolder extends BaseItemViewHolder<CartItem, RowItemCartBinding> {
    private final CartViewModel viewModel;
    private final BookRepository bookRepository;
    private Book book = new Book();

    private static final String TAG = "CartViewHolder";

    public CartItemViewHolder(@NonNull RowItemCartBinding binding,CartViewModel viewModel) {
        super(binding);
        this.viewModel = viewModel;
        bookRepository = new BookRepositoryImpl();
    }

    @Override
    public void bind(CartItem item) {
        bookRepository.getById(item.getBookId(), task -> {
            if (task.isSuccessful()) {
                Book book = task.getResult().toObject(Book.class);
                if (book != null) {
                    binding.textViewQuantity.setText(String.valueOf(item.getQuantity()));
                    binding.textViewBookPrice.setText(String.valueOf(book.getPrice()*item.getQuantity()));
                    binding.textViewBookTitle.setText(book.getTitle());
                    binding.textViewBookAuthor.setText(book.getAuthor());

                    // Use Glide to load book cover image (modify as needed based on where the URL comes from)
                    Glide.with(binding.imageViewBookCover.getContext())
                            .load(book.getBookImg())
                            .into(binding.imageViewBookCover);

                    // Disable increase button if quantity exceeds stock
                    binding.buttonIncreaseQuantity.setEnabled(item.getQuantity() < book.getStock());
                    binding.buttonDecreaseQuantity.setEnabled(item.getQuantity() != 1);

                    binding.buttonIncreaseQuantity.setOnClickListener(v -> {
                        if (item.getQuantity() < book.getStock()) {
                            viewModel.increaseQuantity(item);
                        } else {
                            Toast.makeText(binding.buttonIncreaseQuantity.getContext(), "No more stock available", Toast.LENGTH_SHORT).show();
                        }
                    });

                    binding.buttonDecreaseQuantity.setOnClickListener(v -> {
                        if (item.getQuantity() > 1) {
                            viewModel.decreaseQuantity(item);
                        } else {
                            Toast.makeText(binding.buttonIncreaseQuantity.getContext(), "Minimum quantity is 1", Toast.LENGTH_SHORT).show();
                        }
                    });
                    binding.buttonRemoveItem.setOnClickListener(v -> viewModel.removeItem(item));
                } else {
                    Log.e(TAG, "Book data is null");
                }
            } else {
                Log.e(TAG, "Error getting book by id", task.getException());
            }
        });
    }
}
