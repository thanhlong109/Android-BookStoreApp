package com.group2.bookstoreproject.ui.admin.upsertBook;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Toast;

import com.group2.bookstoreproject.R;
import com.group2.bookstoreproject.base.BaseFragment;
import com.group2.bookstoreproject.base.common.Constants;
import com.group2.bookstoreproject.data.model.Book;
import com.group2.bookstoreproject.data.model.Category;
import com.group2.bookstoreproject.databinding.FragmentUpsertBookBinding;
import com.group2.bookstoreproject.util.DataConverter;
import com.group2.bookstoreproject.util.ValidationUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


public class UpsertBookFragment extends BaseFragment<FragmentUpsertBookBinding, UpsertViewModel> {
    public static final String EMPTY_MESSAGE = "Không được bỏ trống trường này!";
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
            }
            if(MODE == Constants.UPDATE_MODE){
                book = (Book) bundle.getSerializable("book");
            }
        }

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
        binding.upsetBookToolbar.setOnStartIconClick(() -> navigateBack());
        DisplayBook();
    }

    private void DisplayBook(){
        if(MODE == Constants.UPDATE_MODE && book!=null){
            binding.etTitle.setText(book.getTitle());
            binding.etAuthor.setText(book.getAuthor());
            binding.etDescription.setText(book.getDescription());
            binding.etPrice.setText(""+book.getPrice());
            binding.etStock.setText(""+book.getStock());

        }
    }

    private Book getBookInput(){
        AtomicBoolean isError = new AtomicBoolean(false);
        String author = binding.etAuthor.toString();
        String title = binding.etTitle.toString();
        String des = binding.etDescription.toString();
        String price = binding.etPrice.toString();
        String stock = binding.etStock.toString();
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

        int nPrice = DataConverter.IntegerSafeConvert(price,0, () -> {
           isError.set(true);
        });
        int nStock = DataConverter.IntegerSafeConvert(stock,0, () -> {
            isError.set(true);
        });
        int sale = DataConverter.IntegerSafeConvert(binding.tvSaleValue.getText().toString(),0, () -> {
            isError.set(true);
        });

        if(isError.get()) return null;
        Book book = new Book();
        book.setAuthor(author);
        book.setPrice(nPrice);
        book.setStock(nStock);
        book.setTitle(title);
        book.setDescription(des);
        book.setSale(sale);
        book.setCategoryId(selectedCategory.getCategoryId());
        book.setBookImg("");
        book.setPublisher("BookStore");
        return book;
    }
}