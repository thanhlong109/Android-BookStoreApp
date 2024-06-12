package com.group2.bookstoreproject.base;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

public abstract class BaseItemViewHolder<T, VB extends ViewBinding> extends RecyclerView.ViewHolder {

    protected final VB binding;
    protected final Context itemContext;

    public BaseItemViewHolder(@NonNull VB binding) {
        super(binding.getRoot());
        this.binding = binding;
        this.itemContext = binding.getRoot().getContext();
    }

    public abstract void bind(T item);
}

