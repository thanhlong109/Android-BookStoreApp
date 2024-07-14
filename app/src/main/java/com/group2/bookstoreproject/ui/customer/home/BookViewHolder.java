package com.group2.bookstoreproject.ui.customer.home;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.group2.bookstoreproject.R;
import com.group2.bookstoreproject.base.BaseItemViewHolder;
import com.group2.bookstoreproject.data.model.Book;
import com.group2.bookstoreproject.data.model.CartItem;
import com.group2.bookstoreproject.data.repository.BookRepository;
import com.group2.bookstoreproject.data.repositoryImpl.BookRepositoryImpl;
import com.group2.bookstoreproject.databinding.ItemBookBinding;
import com.group2.bookstoreproject.databinding.RowItemCartBinding;
import com.group2.bookstoreproject.ui.customer.cart.CartViewModel;
import com.group2.bookstoreproject.ui.customer.home.BookViewModel;

public class BookViewHolder extends BaseItemViewHolder<Book, ItemBookBinding> {
    

    private static final String TAG = "BookViewHolder";


    public BookViewHolder(@NonNull ItemBookBinding binding) {
        super(binding);
    }



    @Override
    public void bind(Book item) {
        binding.titleTextView.setText(item.getTitle());
        binding.authorTextView.setText(item.getAuthor());
        binding.priceTextView.setText(item.getPrice() + " đ");
        // Load the image using Glide
        Glide.with(binding.imageView.getContext())
                .load(item.getBookImg()) // assuming getBookImgUrl() returns the image URL
                .into(binding.imageView);
    }

}

