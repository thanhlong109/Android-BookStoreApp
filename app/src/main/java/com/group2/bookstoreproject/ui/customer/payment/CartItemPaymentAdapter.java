package com.group2.bookstoreproject.ui.customer.payment;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.group2.bookstoreproject.data.model.CartItem;
import com.group2.bookstoreproject.databinding.RowItemsPaymentBinding;
import java.util.ArrayList;
import java.util.List;

public class CartItemPaymentAdapter extends RecyclerView.Adapter<CartItemPaymentViewHolder> {
    private List<CartItem> cartItems = new ArrayList<>();

    @NonNull
    @Override
    public CartItemPaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RowItemsPaymentBinding binding = RowItemsPaymentBinding.inflate(inflater, parent, false);
        return new CartItemPaymentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemPaymentViewHolder holder, int position) {
        holder.bind(cartItems.get(position));
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public void submitList(List<CartItem> newCartItems) {
        cartItems.clear();
        cartItems.addAll(newCartItems);
        notifyDataSetChanged();
    }
}
