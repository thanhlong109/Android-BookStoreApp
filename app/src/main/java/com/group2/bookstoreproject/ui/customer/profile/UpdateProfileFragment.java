package com.group2.bookstoreproject.ui.customer.profile;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.group2.bookstoreproject.base.BaseFragment;
import com.group2.bookstoreproject.data.model.User;
import com.group2.bookstoreproject.databinding.FragmentUpdateProfileBinding;
import com.group2.bookstoreproject.util.DateUtils;

import java.text.ParseException;
import java.util.Calendar;

public class UpdateProfileFragment extends BaseFragment<FragmentUpdateProfileBinding, UpdateProfileViewModel> {

    @Override
    protected FragmentUpdateProfileBinding inflateBinding(LayoutInflater inflater, ViewGroup container, boolean attachToParent) {
        return FragmentUpdateProfileBinding.inflate(inflater, container, attachToParent);
    }

    @Override
    protected Class<UpdateProfileViewModel> getViewModelClass() {
        return UpdateProfileViewModel.class;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        if (args != null && args.containsKey("UserUD")) {
            User user = (User) args.getSerializable("UserUD");
            if (user != null) {
                binding.etUsername.setText(user.getFullName());
                binding.etBirthdate.setText(DateUtils.formatDate(user.getDateOfBirth(), "dd/MM/yyyy"));
            }
        }

        binding.etBirthdate.setOnClickListener(v -> showDatePickerDialog());

        binding.etBirthdate.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                showDatePickerDialog();
            }
        });

        binding.btnUpdate.setOnClickListener(v -> {
            String fullName = binding.etUsername.getText().toString().trim();
            String birthdateString = binding.etBirthdate.getText().toString().trim();
            User user = (User) args.getSerializable("UserUD");

            if (!birthdateString.isEmpty()) {
                try {
                    long birthdate = DateUtils.parseDateWithDefaultTime(birthdateString, "dd/MM/yyyy");
                    viewModel.updateUserProfile(user.getUserId(), fullName, birthdate);
                    viewModel.loadUserProfile();
                    getParentFragmentManager().popBackStack();
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Invalid date format", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Please enter a birthdate", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnCancel.setOnClickListener(v -> {
            getParentFragmentManager().popBackStack();
        });
    }


    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (view, year, month, dayOfMonth) -> {
                    // Update the EditText with the selected date
                    calendar.set(year, month, dayOfMonth);
                    String selectedDate = DateUtils.formatDate(calendar.getTime(), "dd/MM/yyyy");
                    binding.etBirthdate.setText(selectedDate);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }


}
