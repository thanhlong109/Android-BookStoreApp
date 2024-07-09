package com.group2.bookstoreproject.ui.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.group2.bookstoreproject.R;
import com.group2.bookstoreproject.databinding.FragmentPaymentResultBinding;

public class PaymentResultActivity extends AppCompatActivity {

    private FragmentPaymentResultBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentPaymentResultBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        String args = getIntent().getStringExtra("result");
        if (args != null ){
            binding.tvResult.setText(args);
        }
    }
}