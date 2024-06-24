package com.group2.bookstoreproject.ui.common.signIn;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.group2.bookstoreproject.R;
import com.group2.bookstoreproject.base.BaseFragment;
import com.group2.bookstoreproject.data.model.User;
import com.group2.bookstoreproject.data.model.base.Resource;
import com.group2.bookstoreproject.data.sharePreference.AppSharePreference;
import com.group2.bookstoreproject.databinding.FragmentSignInBinding;
import com.group2.bookstoreproject.ui.activity.AdminActivity;
import com.group2.bookstoreproject.ui.activity.CustomerActivity;
import com.group2.bookstoreproject.ui.activity.ShipperActivity;
import com.group2.bookstoreproject.util.session.SessionManager;

public class SignInFragment extends BaseFragment<FragmentSignInBinding, SignInViewModel> {

    @NonNull
    @Override
    protected FragmentSignInBinding inflateBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, boolean attachToParent) {
        return FragmentSignInBinding.inflate(inflater, container, attachToParent);
    }

    @NonNull
    @Override
    protected Class<SignInViewModel> getViewModelClass() {
        return SignInViewModel.class;
    }

    @Override
    protected void observeViewModel() {
        super.observeViewModel();
        viewModel.getUserLiveData().observe(getViewLifecycleOwner(), new Observer<Resource<User>>() {
            @Override
            public void onChanged(Resource<User> resource) {
                switch (resource.getStatus()) {
                    case SUCCESS:
                        User user = resource.getData();
                        if (user != null) {
                            SessionManager sessionManager = new SessionManager(new AppSharePreference(getContext()));
                            sessionManager.saveUser(user);
                            navigateToActivity(user.getRole());
                        } else {
                            Toast.makeText(getContext(), "User data is null", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case ERROR:
                        Toast.makeText(getContext(), resource.getMessage(), Toast.LENGTH_SHORT).show();
                        break;
                    case LOADING:
                        // Show loading spinner if needed
                        break;
                }
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.button.setOnClickListener(v -> {
            String email = binding.txtUsername.getText().toString();
            String password = binding.txtPassword.getText().toString();
            if (!email.isEmpty() && !password.isEmpty()) {
                viewModel.signIn(getContext(), email, password);
            } else {
                Toast.makeText(getContext(), "Please enter email and password", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnGoSignUp.setOnClickListener(v -> navigateToPage(R.id.action_signInFragment_to_signUpFragment));
    }

    private void navigateToActivity(int role) {
        switch (role) {
            case 1: // Admin
                goToActivity(AdminActivity.class);
                break;
            case 2: // Customer
                goToActivity(CustomerActivity.class);
                break;
            case 3: // Shipper
                goToActivity(ShipperActivity.class);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + role);
        }
    }
}
