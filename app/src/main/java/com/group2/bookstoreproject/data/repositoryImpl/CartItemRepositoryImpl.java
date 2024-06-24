package com.group2.bookstoreproject.data.repositoryImpl;

import com.group2.bookstoreproject.data.model.CartItem;
import com.group2.bookstoreproject.data.repository.CartItemRepository;

public class CartItemRepositoryImpl extends BaseRepositoryImpl<CartItem> implements CartItemRepository {

    private static final String COLLECTION_PATH = "cart_items";

    @Override
    protected String getCollectionPath() {
        return COLLECTION_PATH;
    }
}
