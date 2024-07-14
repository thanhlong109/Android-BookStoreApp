package com.group2.bookstoreproject.ui.admin.upsertBook;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;
import com.group2.bookstoreproject.base.BaseViewModel;
import com.group2.bookstoreproject.base.common.Constants;
import com.group2.bookstoreproject.data.model.Book;
import com.group2.bookstoreproject.data.model.Category;
import com.group2.bookstoreproject.data.model.FCMRequest;
import com.group2.bookstoreproject.data.repository.BookRepository;
import com.group2.bookstoreproject.data.repository.CategoryRepository;
import com.group2.bookstoreproject.data.repository.NotificationRepository;
import com.group2.bookstoreproject.data.repositoryImpl.BookRepositoryImpl;
import com.group2.bookstoreproject.data.repositoryImpl.CategoryRepositoryImpl;
import com.group2.bookstoreproject.data.repositoryImpl.NotificationRepositoryImpl;

import java.util.List;

public class UpsertViewModel extends BaseViewModel {
    private final NotificationRepository notificationRepository = new NotificationRepositoryImpl();
    private final CategoryRepository categoryRepository = new CategoryRepositoryImpl();
    private final BookRepository bookRepository = new BookRepositoryImpl();

    public  void sendNotificationToCustomer(Context context, String title, String message){
        FCMRequest.Notification notification = new FCMRequest.Notification(title, message);
        FCMRequest.Message body = new FCMRequest.Message(Constants.GLOBAL_NOTIFICATION_ID, notification);
        FCMRequest fcmRequest = new FCMRequest(body);
        notificationRepository.sendNotification(fcmRequest,context);
    }

    public void loadCategory(OnLoadDone onLoadDone){
        setLoading(true);
        categoryRepository.getAll(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    List<Category> list  = task.getResult().toObjects(Category.class);
                    onLoadDone.OnLoadDone(list);
                }
                setLoading(false);
            }
        });
    }

    public void  upsertBook(Book book, OnSuccessListener onSuccessListener){
        setLoading(true);
        bookRepository.upsert(book.getBookId(), book, task -> {
            if(task.isSuccessful()){
                onSuccessListener.onSuccess(null);
            }else{
                setErrorMessage(task.getException().getMessage());
            }
            setLoading(false);
        });
    }

    public interface OnLoadDone{
        void OnLoadDone(List<Category> list);
    }

}
