package com.group2.bookstoreproject.ui.admin.upsertBook;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.group2.bookstoreproject.R;
import com.group2.bookstoreproject.base.BaseFragment;
import com.group2.bookstoreproject.base.common.Constants;
import com.group2.bookstoreproject.data.model.Book;
import com.group2.bookstoreproject.data.model.Category;
import com.group2.bookstoreproject.databinding.FragmentUpsertBookBinding;
import com.group2.bookstoreproject.util.DataConverter;
import com.group2.bookstoreproject.util.ValidationUtils;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UpsertBookFragment extends BaseFragment<FragmentUpsertBookBinding, UpsertViewModel> {
    public static final String EMPTY_MESSAGE = "Không được bỏ trống trường này!";
    private static final String TAG = "UpsertBookFragment";
    private static final int PICK_IMAGE_REQUEST = 1;
    private FirebaseStorage firebaseStorage;
    private Uri selectedImage;
    private int MODE;
    private Book book;
    private ArrayAdapter<String> categoryAdapter;
    private Category selectedCategory;
    @NonNull
    @Override
    protected FragmentUpsertBookBinding inflateBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, boolean attachToParent) {
        return FragmentUpsertBookBinding.inflate(inflater,container,attachToParent);
    }

    @NonNull
    @Override
    protected Class<UpsertViewModel> getViewModelClass() {
        return UpsertViewModel.class;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //para
        Bundle bundle = getArguments();
        if(bundle !=null){
            if(bundle.containsKey(Constants.MODE_KEY)){
                MODE = bundle.getInt(Constants.MODE_KEY);
            }else{
                MODE = Constants.CREATE_MODE;
                binding.upsetBookToolbar.isShowEndIcon(false);
            }
            if(MODE == Constants.UPDATE_MODE){
                binding.upsetBookToolbar.isShowEndIcon(true);
                book = (Book) bundle.getSerializable("book");
                binding.upsetBookToolbar.setOnEndIconClick(() -> deleteBook());
            }
        }
        firebaseStorage = FirebaseStorage.getInstance();
        //ui
        binding.seekBarSale.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                binding.tvSaleValue.setText("Sale: "+String.valueOf(progress)+"%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        if(MODE == Constants.UPDATE_MODE){
            binding.seekBarSale.setProgress(book.getSale());
        }
        viewModel.loadCategory(new UpsertViewModel.OnLoadDone() {
            @Override
            public void OnLoadDone(List<Category> list) {
                List<String> textList = new ArrayList<>();

                for (Category category : list) {
                    textList.add(category.getName());
                }
                categoryAdapter = new ArrayAdapter<>(getContext(), R.layout.custom_spinner_item, textList);
                categoryAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
                binding.spinnerCategory.setAdapter(categoryAdapter);
                if(list.size()>0){
                    int index = 0;
                    if(MODE == Constants.UPDATE_MODE){
                        for (int i = 0; i<list.size();i++) {
                            if(list.get(i).getCategoryId().equals(book.getCategoryId())){
                                index = i;
                                break;
                            }
                        }
                    }
                    binding.spinnerCategory.setSelection(index);
                    selectedCategory = list.get(0);
                }
                binding.spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedCategory = list.get(position);
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });
        binding.bookCover.setOnClickListener(v -> openImagePicker());
        binding.upsetBookToolbar.setOnStartIconClick(() -> navigateBack(view));
        binding.btnSaveBook.setOnClickListener(v -> {
            Book data = getBookInput();
            if(data !=null){
                if(selectedImage !=null){
                    uploadImage(data, new OnSuccessListener<String>() {
                        @Override
                        public void onSuccess(String s) {
                            data.setBookImg(s);
                            upsert(data);
                        }
                    });
                }else {
                    upsert(data);
                }

            }else{
                Log.d(TAG, "null");
            }

        });
        DisplayBook();
    }

    private void upsert(Book data){
        viewModel.upsertBook(data, new OnSuccessListener() {
            @Override
            public void onSuccess(Object object) {
                showToast("Lưu sách thành công!");
                if(MODE == Constants.CREATE_MODE){
                    viewModel.sendNotificationToCustomer(requireContext(),"Sách mới!","Sách "+data.getTitle()+" mới được thêm vào!");
                }
                navigateBack();
            }
        });
    }




    private void DisplayBook(){
        if(MODE == Constants.UPDATE_MODE && book!=null){
            binding.etTitle.setText(book.getTitle());
            binding.etAuthor.setText(book.getAuthor());
            binding.etDescription.setText(book.getDescription());
            binding.etPrice.setText(""+book.getPrice());
            binding.etStock.setText(""+book.getStock());
            Glide.with(getContext()).load(book.getBookImg()).into(binding.bookCover);
        }
    }

    private void deleteBook(){
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Xóa sách");
        builder.setMessage("Bạn có chắc muốn xóa " + book.getTitle() + "?");
        builder.setPositiveButton("Có", (dialog, which) -> {
           viewModel.deleteBook(book.getBookId());
            navigateToPage(R.id.action_upsertBookFragment_to_navigation_books);
        });
        builder.setNegativeButton("Không", (dialog, which) -> {
            dialog.dismiss();
        });
        builder.show();

    }

    private Book getBookInput(){
        AtomicBoolean isError = new AtomicBoolean(false);
        String author = binding.etAuthor.getText().toString();
        String title = binding.etTitle.getText().toString();
        String des = binding.etDescription.getText().toString();
        String price = binding.etPrice.getText().toString();
        String stock = binding.etStock.getText().toString();
        if(ValidationUtils.isEmpty(author)){
            binding.tilAuthor.setError(EMPTY_MESSAGE);
            isError.set(true);
        }
        if(ValidationUtils.isEmpty(title)){
            binding.tilTitle.setError(EMPTY_MESSAGE);
            isError.set(true);
        }
        if(ValidationUtils.isEmpty(des)){
            binding.tilDescription.setError(EMPTY_MESSAGE);
            isError.set(true);
        }
        if(ValidationUtils.isEmpty(price)){
            binding.tilPrice.setError(EMPTY_MESSAGE);
            isError.set(true);
        }
        if(ValidationUtils.isEmpty(stock)){
            binding.etStock.setError(EMPTY_MESSAGE);
            isError.set(true);
        }

        double nPrice = DataConverter.DoubleSafeConvert(price,0, () -> {
           isError.set(true);
            showToast("Giá không hợp lệ!");
        });
        int nStock = DataConverter.IntegerSafeConvert(stock,0, () -> {
            isError.set(true);
            showToast("Số lượng không hợp lệ!");
        });
        int sale = binding.seekBarSale.getProgress();
        if(selectedImage == null){
            showToast("Vui lòng chọn ảnh");
            isError.set(true);
        }
        if(isError.get()) return null;
        Book newBook = new Book();
        if(MODE == Constants.UPDATE_MODE){
            newBook.setBookId(book.getBookId());
            newBook.setBookImg(book.getBookImg());
        }
        newBook.setAuthor(author);
        newBook.setPrice(nPrice);
        newBook.setStock(nStock);
        newBook.setTitle(title);
        newBook.setDescription(des);
        newBook.setSale(sale);
        newBook.setCategoryId(selectedCategory.getCategoryId());
        newBook.setPublisher("BookStore");
        return newBook;
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImage = data.getData();
            Glide.with(getContext()).load(selectedImage).into(binding.bookCover);
        }
    }

    private void uploadImage(Book book, OnSuccessListener<String> onSuccessListener) {
        showLoading(true);
        String path = "books/" + book.getBookId() + ".jpg";
        StorageReference storageRef = firebaseStorage.getReference().child(path);
        UploadTask uploadTask = storageRef.putFile(selectedImage);
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                onSuccessListener.onSuccess(uri.toString());
                hideLoading();
            });
        }).addOnFailureListener(e -> {
            Log.e(TAG, "Failed to upload image", e);
            showErrorMessage("Up file không thành công!");
            hideLoading();
        });
    }
}