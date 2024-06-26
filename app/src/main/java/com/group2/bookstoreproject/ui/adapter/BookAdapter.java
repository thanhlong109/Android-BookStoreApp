package com.group2.bookstoreproject.ui.adapter;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.group2.bookstoreproject.R;
import com.group2.bookstoreproject.base.BaseAdapter;
import com.group2.bookstoreproject.data.model.Book;

import java.util.ArrayList;
import java.util.List;
public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder>{

    private static OnItemClickListener onItemClickListener;
    private List<Book> books = new ArrayList<>();

    public void setBooks(List<Book> newBooks) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new BookDiffCallback(books, newBooks));
        books.clear();
        books.addAll(newBooks);
        diffResult.dispatchUpdatesTo(this);
    }

    public interface OnItemClickListener {
        void onItemClick(Book book);
    }

    public BookAdapter(OnItemClickListener onItemClickListener) {
        BookAdapter.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_category, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = books.get(position);
        holder.titleTextView.setText(book.getTitle());
        Glide.with(holder.itemView.getContext())
                .load(book.getBookImg())
                .placeholder(R.drawable.default_user_avt)
                .into(holder.imageView);
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(book);
            }
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleTextView;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.category_image);
            titleTextView = itemView.findViewById(R.id.category_name);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && onItemClickListener != null) {
                    onItemClickListener.onItemClick((Book) v.getTag());
                }
            });
        }
    }

    static class BookDiffCallback extends DiffUtil.Callback {
        private final List<Book> oldList;
        private final List<Book> newList;

        public BookDiffCallback(List<Book> oldList, List<Book> newList) {
            this.oldList = oldList;
            this.newList = newList;
        }

        @Override
        public int getOldListSize() {
            return oldList.size();
        }

        @Override
        public int getNewListSize() {
            return newList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).getBookId().equals(newList.get(newItemPosition).getBookId());
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            Book oldBook = oldList.get(oldItemPosition);
            Book newBook = newList.get(newItemPosition);
            return oldBook.getTitle().equals(newBook.getTitle()) &&
                    oldBook.getBookImg().equals(newBook.getBookImg());
        }
    }
}
