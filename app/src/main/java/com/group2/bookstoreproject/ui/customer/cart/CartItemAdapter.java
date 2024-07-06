package com.group2.bookstoreproject.ui.customer.cart;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.group2.bookstoreproject.base.BaseAdapter;
import com.group2.bookstoreproject.data.model.CartItem;
import com.group2.bookstoreproject.databinding.RowItemCartBinding;

public class CartItemAdapter extends BaseAdapter<CartItem, CartItemViewHolder> {
    private final CartViewModel viewModel;

    public CartItemAdapter(CartViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    protected CartItemViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent, int viewType) {
        return new CartItemViewHolder(RowItemCartBinding.inflate(inflater, parent, false), viewModel);
    }

    @Override
    protected DiffUtil.ItemCallback<CartItem> differCallBack() {
        return new DiffUtil.ItemCallback<CartItem>() {
            @Override
            public boolean areItemsTheSame(@NonNull CartItem oldItem, @NonNull CartItem newItem) {
                return oldItem.getCartItemId().equals(newItem.getCartItemId());
            }

            @Override
            public boolean areContentsTheSame(@NonNull CartItem oldItem, @NonNull CartItem newItem) {
                return oldItem.equals(newItem);
            }
        };
    }
}