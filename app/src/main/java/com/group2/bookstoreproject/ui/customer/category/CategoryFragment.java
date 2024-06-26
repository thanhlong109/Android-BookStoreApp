package com.group2.bookstoreproject.ui.customer.category;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.group2.bookstoreproject.R;
import com.group2.bookstoreproject.base.BaseFragment;
import com.group2.bookstoreproject.data.model.Book;
import com.group2.bookstoreproject.data.model.Category;
import com.group2.bookstoreproject.databinding.FragmentCategoryBinding;
import com.group2.bookstoreproject.ui.adapter.BookAdapter;
import com.group2.bookstoreproject.ui.common.bookDetails.BookDetailsFragment;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends BaseFragment<FragmentCategoryBinding,CategoryViewModel> {
    private BookAdapter bookAdapter = new BookAdapter(book -> {
        Bundle bundle = new Bundle();
        bundle.putSerializable("book", book);
       // bundle.putInt("scroll_position", ((GridLayoutManager) binding.recyclerView.getLayoutManager()).findFirstVisibleItemPosition());
       // bundle.putInt("selected_category", binding.categorySpinner.getSelectedItemPosition());
        NavController navController = NavHostFragment.findNavController(CategoryFragment.this);
        navController.navigate(R.id.action_navigation_category_to_bookDetailsFragment2, bundle);
    });
    @NonNull
    @Override
    protected FragmentCategoryBinding inflateBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, boolean attachToParent) {
        return FragmentCategoryBinding.inflate(inflater,container,attachToParent);
    }

    @NonNull
    @Override
    protected Class<CategoryViewModel> getViewModelClass() {
        return CategoryViewModel.class;
    }

    private int selectedCategoryPosition = 0;
    private int scrollPosition = 0;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState != null) {
            selectedCategoryPosition = savedInstanceState.getInt("selected_category_position", 0);
        }

        binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.recyclerView.setAdapter(bookAdapter);

        viewModel.getBooks().observe(getViewLifecycleOwner(), bookAdapter::setBooks);
        viewModel.fetchBooks();

        viewModel.getCategories().observe(getViewLifecycleOwner(), categories -> {
            List<String> categoryNames = new ArrayList<>();
            for (Category category : categories) {
                categoryNames.add(category.getName());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, categoryNames);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            binding.categorySpinner.setAdapter(adapter);
            // Kiểm tra và thiết lập lại vị trí đã lưu
            if (selectedCategoryPosition < categoryNames.size()) {
                binding.categorySpinner.setSelection(selectedCategoryPosition);
            }

            binding.categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String categoryName = parent.getItemAtPosition(position).toString();
                    viewModel.filterByCategory(categoryName);
                    selectedCategoryPosition = position;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // Handle nothing selected if needed
                }
            });
           // binding.categorySpinner.setSelection(selectedCategory);
        });
        viewModel.fetchCategories();

        if (savedInstanceState == null) {
            viewModel.fetchCategories();
        }
        if (savedInstanceState != null) {
            scrollPosition = savedInstanceState.getInt("scroll_position", 0);
        }
        EditText searchEditText = binding.searchView.searchSrcText.findViewById(R.id.search_src_text);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setSearchQuery(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        viewModel.getFilteredBooks().observe(getViewLifecycleOwner(), bookAdapter::setBooks);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.price_filter_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.priceFilterSpinner.setAdapter(adapter);
        binding.priceFilterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    viewModel.filterByPriceIncrease();
                } else if (position == 1) {
                    viewModel.filterByPriceDecrease();
                } else if (position == 2) {
                    viewModel.filterBySaleIncrease();
                } else if (position == 3) {
                    viewModel.filterBySaleDecrease();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.priceFilterSpinner.setSelection(0);
        viewModel.filterByPriceIncrease();

//        binding.categorySpinner.setSelection(0);
//        viewModel.filterByCategory("a");

        binding.recyclerView.post(() -> {
            binding.recyclerView.scrollToPosition(scrollPosition);
        });

//        binding.btnAdd.setOnClickListener(v -> {
////            Category category = new Category();
////            category.setCategoryId("4");
////            category.setName("Anime");
////            Log.v("CategoryViewModel","thdhdfh");
////            viewModel.AddCategory(category);
//
//            Book book = new Book();
//            book.setBookId("11"); // or generate a unique ID
//            book.setTitle("Book 11");
//            book.setAuthor("Author Name");
//            book.setBookImg("image_url");
//            book.setPublisher("Publisher Name");
//            book.setPrice(9.99);
//            book.setDescription("Sample Description");
//            book.setCategoryId("3");
//            book.setStock(10);
//            book.setSale(2);
//            viewModel.AddBook(book);
//        });


    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("selected_category_position", selectedCategoryPosition);
        outState.putInt("scroll_position", ((GridLayoutManager) binding.recyclerView.getLayoutManager()).findFirstVisibleItemPosition());
    }

}