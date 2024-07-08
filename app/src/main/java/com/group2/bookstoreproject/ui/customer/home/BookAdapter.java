package com.group2.bookstoreproject.ui.customer.home;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.group2.bookstoreproject.base.BaseAdapter;
import com.group2.bookstoreproject.data.model.Book;
import com.group2.bookstoreproject.databinding.ItemBookBinding;

import java.util.List;

public class BookAdapter extends BaseAdapter<Book, BookViewHolder> {


    @Override
    protected BookViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent, int viewType) {
        return new BookViewHolder(ItemBookBinding.inflate(inflater, parent, false));
    }

    @Override
    protected DiffUtil.ItemCallback<Book> differCallBack() {
        return new DiffUtil.ItemCallback<Book>() {
            @Override
            public boolean areItemsTheSame(@NonNull Book oldItem, @NonNull Book newItem) {
                return oldItem.getBookId().equals(newItem.getBookId());
            }

            @SuppressLint("DiffUtilEquals")
            @Override
            public boolean areContentsTheSame(@NonNull Book oldItem, @NonNull Book newItem) {
                return oldItem.equals(newItem);
            }
        } ;
    }

    @Override
    public void setItemOnClickListener(OnItemClickListener<Book> listener) {
        super.setItemOnClickListener(listener);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }
}
