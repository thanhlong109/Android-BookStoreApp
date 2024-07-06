package com.group2.bookstoreproject.ui.common.bookDetails;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.group2.bookstoreproject.R;
import com.group2.bookstoreproject.base.BaseFragment;
import com.group2.bookstoreproject.data.model.Book;
import com.group2.bookstoreproject.data.model.CartItem;
import com.group2.bookstoreproject.data.model.User;
import com.group2.bookstoreproject.data.repository.CartItemRepository;
import com.group2.bookstoreproject.databinding.FragmentBookDetailsBinding;
import com.group2.bookstoreproject.databinding.FragmentChatBinding;
import com.group2.bookstoreproject.util.session.SessionManager;

import java.util.Random;
import java.util.UUID;


public class BookDetailsFragment extends BaseFragment<FragmentBookDetailsBinding, BookDetailsViewModel> {
    private boolean isHeaderTitleSet = false;
    private String accountId;

    private static final int[] backgroundImages = {
            R.drawable.imgb_background1,
            R.drawable.imgb_background3,
            R.drawable.imgb_background2,
            R.drawable.imgb_background4
            // Thêm các tài nguyên hình ảnh khác vào đây nếu cần
    };

    @NonNull
    @Override
    protected FragmentBookDetailsBinding inflateBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, boolean attachToParent) {
        return FragmentBookDetailsBinding.inflate(inflater, container,attachToParent);
    }

    @NonNull
    @Override
    protected Class<BookDetailsViewModel> getViewModelClass() {
        return BookDetailsViewModel.class;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            Book book = (Book) getArguments().getSerializable("book");
            if (book != null) {
                displayBookDetails(book);
            }
        }
        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().onBackPressed();
            }
        });

        binding.nestedScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                updateHeaderTitle();
            }
        });

        int randomIndex = new Random().nextInt(backgroundImages.length);
        int selectedBackground = backgroundImages[randomIndex];
        float alpha = 1.0f;
        if (selectedBackground == R.drawable.imgb_background1) {
            alpha = 0.8f;
        } else if (selectedBackground == R.drawable.imgb_background3) {
            alpha = 0.9f;
        } else if (selectedBackground == R.drawable.imgb_background2) {
            alpha =1.4f;
        } else if (selectedBackground == R.drawable.imgb_background4) {
            alpha = 0.9f;
        }
        binding.backgroundImage.setImageResource(selectedBackground);
        binding.backgroundImage.setAlpha(alpha);

        binding.addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManager sessionManager = SessionManager.getInstance();
                User loggedInUser = sessionManager.getLoggedInUser();
                if (loggedInUser != null) {
                    // Get the account ID from the logged-in user
                    String accountId = loggedInUser.getUserId();

                    // Check if accountId is not null or empty
                    if (accountId != null && !accountId.isEmpty()) {
                        // Create a new CartItem
                        CartItem item = new CartItem();
                        Book book = (Book) getArguments().getSerializable("book");

                        item.setCartItemId(UUID.randomUUID().toString());
                        item.setBookId(book.getBookId());
                        item.setAccountId(accountId);
                        item.setQuantity(1);
                        item.setPrice(book.getPrice());

                        // Add item to cart using ViewModel
                        viewModel.addToCart(item);
                        Toast.makeText(getContext(), "Added to cart success", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Account ID is missing. Please login again.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "User is not logged in. Please login to add items to cart.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateHeaderTitle() {
        int[] bookTitleLocation = new int[2];
        binding.bookTitle.getLocationOnScreen(bookTitleLocation);
        int bookTitleY = bookTitleLocation[1] + binding.bookTitle.getHeight();

        int[] headerLocation = new int[2];
        binding.headerLayout.getLocationOnScreen(headerLocation);
        int headerBottomY = headerLocation[1] + binding.headerLayout.getHeight();

        if (bookTitleY <= headerBottomY) {
            // Book title is within or above header bounds, update header text
            if (!isHeaderTitleSet) {
                binding.headerTitle.setText(binding.bookTitle.getText());
                isHeaderTitleSet = true;
            }
        } else {
            // Book title is below header bounds, reset header text to default
            if (isHeaderTitleSet) {
                binding.headerTitle.setText("Classics");
                isHeaderTitleSet = false;
            }
        }
    }

    private void displayBookDetails(Book book) {
        binding.bookTitle.setText(book.getTitle());
        binding.author.setText(book.getAuthor());
        binding.price.setText(String.valueOf(book.getPrice()));
        binding.description.setText(book.getDescription());
        binding.sale.setText( String.valueOf(book.getSale()));
        binding.publisher.setText(book.getPublisher());
        Glide.with(requireContext())
                .load(book.getBookImg())
                .into(binding.bookCover);
    }
}