package com.group2.bookstoreproject.ui.customer.payment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.group2.bookstoreproject.R;
import com.group2.bookstoreproject.base.BaseFragment;
import com.group2.bookstoreproject.databinding.FragmentPaymentBinding;
import com.group2.bookstoreproject.databinding.FragmentPaymentResultBinding;


public class PaymentResultFragment extends BaseFragment<FragmentPaymentResultBinding, PaymentViewModel>{

    @NonNull
    @Override
    protected FragmentPaymentResultBinding inflateBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, boolean attachToParent) {
        return FragmentPaymentResultBinding.inflate(inflater, container, attachToParent);
    }

    @NonNull
    @Override
    protected Class<PaymentViewModel> getViewModelClass() {
        return PaymentViewModel.class;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        if (args != null && args.containsKey("result")){
            String result = args.getString("result");
            binding.tvResult.setText(result);
        }

    }
}