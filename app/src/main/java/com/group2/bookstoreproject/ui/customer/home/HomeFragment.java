package com.group2.bookstoreproject.ui.customer.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
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
import com.group2.bookstoreproject.databinding.FragmentHomeBinding;
import com.group2.bookstoreproject.ui.customer.category.CategoryFragment;

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
                        Book book = document.toObject(Book.class);
                        books.add(book);
                    }
                    BookAdapter adapter = new BookAdapter();
                    adapter.setItemOnClickListener(new BookAdapter.OnItemClickListener<Book>() {
                        @Override
                        public void onItemClick(Book book) {
                            NavController navController = NavHostFragment.findNavController(HomeFragment.this);
                            if (navController!= null) {
                                try {
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("book", book);
                                    navController.navigate(R.id.action_navigation_cus_home_to_bookDetailsFragment2, bundle);
                                } catch (Exception e) {
                                    Log.e("HomeFragment", "Error navigating to book details", e);
                                }
                            } else {
                                Log.e("HomeFragment", "NavController is null");
                            }
                        }
                    });
                    recyclerView1.setAdapter(adapter);
                    recyclerView1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                    adapter.submitList(books);
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
                        Book book = document.toObject(Book.class);
                        books.add(book);
                    }
                    BookAdapter adapter = new BookAdapter();
                    adapter.setItemOnClickListener(new BookAdapter.OnItemClickListener<Book>() {
                        @Override
                        public void onItemClick(Book book) {
                            NavController navController = NavHostFragment.findNavController(HomeFragment.this);
                            if (navController!= null) {
                                try {
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("book", book);
                                    navController.navigate(R.id.action_navigation_cus_home_to_bookDetailsFragment2, bundle);
                                } catch (Exception e) {
                                    Log.e("HomeFragment", "Error navigating to book details", e);
                                }
                            } else {
                                Log.e("HomeFragment", "NavController is null");
                            }
                        }
                    });
                    recyclerView2.setAdapter(adapter);
                    recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                    adapter.submitList(books);
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
                        Book book = document.toObject(Book.class);
                        books.add(book);
                    }
                    BookAdapter adapter = new BookAdapter();
                    adapter.setItemOnClickListener(new BookAdapter.OnItemClickListener<Book>() {
                        @Override
                        public void onItemClick(Book book) {
                            NavController navController = NavHostFragment.findNavController(HomeFragment.this);
                            if (navController!= null) {
                                try {
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("book", book);
                                    navController.navigate(R.id.action_navigation_cus_home_to_bookDetailsFragment2, bundle);
                                } catch (Exception e) {
                                    Log.e("HomeFragment", "Error navigating to book details", e);
                                }
                            } else {
                                Log.e("HomeFragment", "NavController is null");
                            }
                        }
                    });
                    recyclerView3.setAdapter(adapter);
                    recyclerView3.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                    adapter.submitList(books);
                } else {
                    // Handle error
                    Toast.makeText(getContext(), "Error loading best selling books: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}