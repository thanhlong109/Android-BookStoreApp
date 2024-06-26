package com.group2.bookstoreproject.ui.common.bookDetails;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.group2.bookstoreproject.R;
import com.group2.bookstoreproject.base.BaseFragment;
import com.group2.bookstoreproject.data.model.Book;
import com.group2.bookstoreproject.databinding.FragmentBookDetailsBinding;
import com.group2.bookstoreproject.databinding.FragmentChatBinding;

import java.util.Random;


public class BookDetailsFragment extends BaseFragment<FragmentBookDetailsBinding, BookDetailsViewModel> {
    private boolean isHeaderTitleSet = false;

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