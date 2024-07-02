package com.group2.bookstoreproject.ui.customer.profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;

import com.bumptech.glide.Glide;
import com.group2.bookstoreproject.R;
import com.group2.bookstoreproject.base.BaseFragment;
import com.group2.bookstoreproject.data.model.User;
import com.group2.bookstoreproject.databinding.FragmentProfileBinding;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.group2.bookstoreproject.util.DateUtils;
import com.group2.bookstoreproject.util.session.SessionManager;

import java.io.ByteArrayOutputStream;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends BaseFragment<FragmentProfileBinding, ProfileViewModel> {

    private static final String TAG = "ProfileFragment";
    private static final int PICK_IMAGE_REQUEST = 1;
    private FirebaseStorage firebaseStorage;

    @NonNull
    @Override
    protected FragmentProfileBinding inflateBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, boolean attachToParent) {
        return FragmentProfileBinding.inflate(inflater, container, attachToParent);
    }

    @NonNull
    @Override
    protected Class<ProfileViewModel> getViewModelClass() {
        return ProfileViewModel.class;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseStorage = FirebaseStorage.getInstance();

        viewModel.getUserLiveData().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                hideLoading(); // Hide loading once the user profile is loaded
                if (user != null) {
                    binding.tvFullName.setText(user.getFullName());
                    binding.tvEmail.setText(user.getEmail());
                    binding.tvPhone.setText(user.getPhone());
                    binding.tvBirthdate.setText(DateUtils.formatDate(user.getDateOfBirth(), "dd/MM/yyyy"));

                    // Load user avatar image if available
                    if (user.getAvatar() != null) {
                        Glide.with(ProfileFragment.this)
                                .load(user.getAvatar())
                                .into(binding.imgAvatar);
                    }
                } else {
                    Log.d(TAG, "User not found");
                }
            }
        });

        viewModel.getErrorLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String error) {
                hideLoading(); // Hide loading on error
                if (error != null) {
                    Log.e(TAG, error);
                }
            }
        });

        showLoading(true); // Show loading before starting to load the user profile
        viewModel.loadUserProfileUD();

        binding.imageViewAdd.setOnClickListener(v -> openImagePicker());

        binding.imgSetting.setOnClickListener(v -> openUpdateProfileFragment());
    }

    private void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void openUpdateProfileFragment() {
        User currentUser = viewModel.getUserLiveData().getValue(); // Get current user
        if (currentUser != null) {
            FragmentManager fragmentManager = getParentFragmentManager();
            UpdateProfileFragment fragment = new UpdateProfileFragment();

            // Pass user data to UpdateProfileFragment using Bundle
            Bundle args = new Bundle();
            args.putSerializable("UserUD", currentUser);
            fragment.setArguments(args);

            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        } else {
            Log.e(TAG, "Current user is null");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                uploadImage(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage(Bitmap bitmap) {
        showLoading(true);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        String userId = viewModel.getUserLiveData().getValue().getUserId();
        if (userId == null || userId.isEmpty()) {
            Log.e(TAG, "User ID is null or empty");
            hideLoading();
            return;
        }

        String path = "avatars/" + userId + ".jpg";
        StorageReference storageRef = firebaseStorage.getReference().child(path);
        UploadTask uploadTask = storageRef.putBytes(data);

        uploadTask.addOnSuccessListener(taskSnapshot -> {
            storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                String imageUrl = uri.toString();
                viewModel.updateUserAvatar(imageUrl);
                hideLoading();
            });
        }).addOnFailureListener(e -> {
            Log.e(TAG, "Failed to upload image", e);
            hideLoading();
        });
    }
}