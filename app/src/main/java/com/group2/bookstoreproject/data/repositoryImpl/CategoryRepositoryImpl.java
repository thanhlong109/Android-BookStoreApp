package com.group2.bookstoreproject.data.repositoryImpl;

import com.group2.bookstoreproject.data.model.Category;
import com.group2.bookstoreproject.data.repository.CategoryRepository;

public class CategoryRepositoryImpl extends BaseRepositoryImpl<Category> implements CategoryRepository {
    private static final String COLLECTION_PATH = "categories";
    @Override
    protected String getCollectionPath() {
        return COLLECTION_PATH;
    }
}
