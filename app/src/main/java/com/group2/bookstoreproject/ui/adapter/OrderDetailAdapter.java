package com.group2.bookstoreproject.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.protobuf.StringValue;
import com.group2.bookstoreproject.R;
import com.group2.bookstoreproject.data.model.Book;
import com.group2.bookstoreproject.data.model.Order;
import com.group2.bookstoreproject.data.model.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.OrderDetailViewHolder> {

    private List<OrderItem> orderItems = new ArrayList<>();
    private List<Book> books = new ArrayList<>();

    public List<OrderItem> getOrderItem(){
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
        notifyDataSetChanged();
    }
    public void setBooks(List<Book> books){
        this.books = books;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public OrderDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_orders_detail, parent, false);
        return new OrderDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailViewHolder holder, int position) {
        OrderItem orderItem = orderItems.get(position);
        holder.tvQuantity.setText(String.valueOf(orderItem.getQuantity()));
        holder.tvPrice.setText(String.valueOf(orderItem.getPrice()));

//        Book book = books.get(position);
//        holder.tvBookName.setText(book.getTitle());
//        holder.tvAuthor.setText(book.getAuthor());
        if(position< books.size()){
            Book book = books.get(position);
            holder.tvBookName.setText(book.getTitle());
            holder.tvAuthor.setText(book.getAuthor());
            Glide.with(holder.itemView.getContext())
                    .load(book.getBookImg())
                    .placeholder(R.drawable.default_user_avt)
                    .into(holder.imgBook);
        }
    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }



    static class OrderDetailViewHolder extends RecyclerView.ViewHolder {

        TextView tvQuantity;
        TextView tvPrice;
        TextView tvBookName;
        TextView tvAuthor;
        ImageView imgBook;

        public OrderDetailViewHolder(@NonNull View itemView) {
            super(itemView);

            tvQuantity = itemView.findViewById(R.id.tvProductOriginalPrice);
            tvPrice = itemView.findViewById(R.id.tvProductPrice);
            tvBookName = itemView.findViewById(R.id.tvBookName);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            imgBook = itemView.findViewById(R.id.ivProductImage);
        }


    }

}
