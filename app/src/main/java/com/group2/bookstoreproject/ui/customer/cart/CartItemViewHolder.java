package com.group2.bookstoreproject.ui.customer.cart;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.group2.bookstoreproject.base.BaseItemViewHolder;
import com.group2.bookstoreproject.data.model.CartItem;
import com.group2.bookstoreproject.databinding.RowItemCartBinding;

public class CartItemViewHolder extends BaseItemViewHolder<CartItem, RowItemCartBinding> {
    private final CartViewModel viewModel;

    public CartItemViewHolder(@NonNull RowItemCartBinding binding,CartViewModel viewModel) {
        super(binding);
        this.viewModel = viewModel;
    }

    @Override
    public void bind(CartItem item) {
        binding.textViewQuantity.setText(String.valueOf(item.getQuantity()));
        binding.textViewBookPrice.setText(String.format("$%.2f", item.getPrice()));

        // Use Glide to load book cover image (modify as needed based on where the URL comes from)
//        Glide.with(binding.imageViewBookCover.getContext())
//                .load(//)
//                .into(binding.imageViewBookCover);

        binding.buttonIncreaseQuantity.setOnClickListener(v -> viewModel.increaseQuantity(item));
        binding.buttonDecreaseQuantity.setOnClickListener(v -> viewModel.decreaseQuantity(item));
        binding.buttonRemoveItem.setOnClickListener(v -> viewModel.removeItem(item));

    }
}
