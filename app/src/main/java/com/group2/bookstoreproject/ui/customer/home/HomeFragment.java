package com.group2.bookstoreproject.ui.customer.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.group2.bookstoreproject.R;
import com.group2.bookstoreproject.base.BaseFragment;
import com.group2.bookstoreproject.data.model.Book;
import com.group2.bookstoreproject.databinding.FragmentCartBinding;
import com.group2.bookstoreproject.databinding.FragmentHomeBinding;
import com.group2.bookstoreproject.ui.adapter.BookAdapter2;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends BaseFragment<FragmentHomeBinding,HomeViewHolder> {

    private RecyclerView recyclerView1;
    private RecyclerView recyclerView2;
    private RecyclerView recyclerView3;
    private HomeViewHolder homeViewHolder;
    @NonNull
    @Override
    protected FragmentHomeBinding inflateBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, boolean attachToParent) {
        return FragmentHomeBinding.inflate(inflater, container,attachToParent);
    }

    @NonNull
    @Override
    protected Class<HomeViewHolder> getViewModelClass() {
        return HomeViewHolder.class;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView1 = binding.recyclerView1;
        recyclerView2 = binding.recyclerView2;
        recyclerView3 = binding.recyclerView3;

        HomeViewHolder homeViewHolder = new HomeViewHolder();

        homeViewHolder.loadNewestBooks(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    List<Book> books = new ArrayList<>();
                    for (QueryDocumentSnapshot document : querySnapshot) {
                        Book book = new Book();
                        book.setTitle(document.getString("title"));
                        book.setAuthor(document.getString("author"));
                        book.setPrice(Double.parseDouble(document.getString("price")));
                        book.setBookImg(document.getString("imageUrl"));
                        books.add(book);
                    }
                    BookAdapter2 adapter = new BookAdapter2(books);
                    recyclerView1.setAdapter(adapter);
                    recyclerView1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                } else {
                    // Handle error
                    Toast.makeText(getContext(), "Error loading newest books: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        homeViewHolder.loadBooksWithHighestStock(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    List<Book> books = new ArrayList<>();
                    for (QueryDocumentSnapshot document : querySnapshot) {
                        Book book = new Book();
                        book.setTitle(document.getString("title"));
                        book.setAuthor(document.getString("author"));
                        book.setPrice(Double.parseDouble(document.getString("price")));
                        book.setBookImg(document.getString("imageUrl"));
                        books.add(book);
                    }
                    BookAdapter2 adapter = new BookAdapter2(books);
                    recyclerView2.setAdapter(adapter);
                    recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                } else {
                    // Handle error
                    Toast.makeText(getContext(), "Error loading books with highest stock: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Load data for recyclerView3
        homeViewHolder.loadBestSellingBooks(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    List<Book> books = new ArrayList<>();
                    for (QueryDocumentSnapshot document : querySnapshot) {
                        Book book = new Book();
                        book.setTitle(document.getString("title"));
                        book.setAuthor(document.getString("author"));
                        book.setPrice(Double.parseDouble(document.getString("price")));
                        book.setBookImg(document.getString("imageUrl"));
                        books.add(book);
                    }
                    BookAdapter2 adapter = new BookAdapter2(books);
                    recyclerView3.setAdapter(adapter);
                    recyclerView3.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                } else {
                    // Handle error
                    Toast.makeText(getContext(), "Error loading best selling books: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}