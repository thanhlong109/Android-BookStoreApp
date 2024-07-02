package com.group2.bookstoreproject.ui.admin.bookManager;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.group2.bookstoreproject.base.BaseAdapter;
import com.group2.bookstoreproject.data.model.Book;
import com.group2.bookstoreproject.data.model.CartItem;
import com.group2.bookstoreproject.databinding.RowItemBookBinding;

public class BookItemAdapter extends BaseAdapter<Book,BookManagerViewHolder> {
    private final BookManagerViewModel bookManagerViewModel;

    public BookItemAdapter(BookManagerViewModel bookManagerViewModel) {
        this.bookManagerViewModel = bookManagerViewModel;
    }

    @Override
    protected BookManagerViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent, int viewType) {
        return new BookManagerViewHolder(RowItemBookBinding.inflate(inflater,parent,false),bookManagerViewModel);
    }

    @Override
    protected DiffUtil.ItemCallback<Book> differCallBack() {
        return new DiffUtil.ItemCallback<Book>() {
            @Override
            public boolean areItemsTheSame(@NonNull Book oldItem, @NonNull Book newItem) {
                return oldItem.getBookId().equals(newItem.getBookId());
            }

            @Override
            public boolean areContentsTheSame(@NonNull Book oldItem, @NonNull Book newItem) {
                return oldItem.equals(newItem);
            }
        };
    }
}
