package com.group2.bookstoreproject.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.group2.bookstoreproject.R;
import com.group2.bookstoreproject.data.model.Book;

import java.util.List;

public class BookAdapter2 extends RecyclerView.Adapter<BookAdapter2.BookViewHolder> {
    @NonNull
    @Override
    public BookAdapter2.BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapter2.BookViewHolder holder, int position) {
        Book book = books.get(position);
        holder.titleTextView.setText(book.getTitle());
        holder.authorTextView.setText(book.getAuthor());
        holder.priceTextView.setText((int) book.getPrice());
        // Load the image using Glide or Picasso
        Glide.with(holder.imageView.getContext()).load(book.getBookImg()).into(holder.imageView);
    }

    private List<Book> books;

    public BookAdapter2(List<Book> books) {
        this.books = books;
    }




    @Override
    public int getItemCount() {
        return books.size();
    }
    public class BookViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView authorTextView;
        private TextView priceTextView;
        private ImageView imageView;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            authorTextView = itemView.findViewById(R.id.authorTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }


}
