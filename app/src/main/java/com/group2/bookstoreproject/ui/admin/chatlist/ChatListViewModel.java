package com.group2.bookstoreproject.ui.admin.chatlist;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.DocumentChange;
import com.group2.bookstoreproject.base.BaseViewModel;
import com.group2.bookstoreproject.data.model.ChatListItem;
import com.group2.bookstoreproject.data.model.ChatRoom;
import com.group2.bookstoreproject.data.model.User;
import com.group2.bookstoreproject.data.repository.AuthRepository;
import com.group2.bookstoreproject.data.repository.ChatRoomRepository;
import com.group2.bookstoreproject.data.repositoryImpl.AuthRepositoryImpl;
import com.group2.bookstoreproject.data.repositoryImpl.ChatRoomRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

public class ChatListViewModel extends BaseViewModel {
    private final  String TAG = "ChatListViewModel";
    private MutableLiveData<List<ChatListItem>> chatRooms;

    private final ChatRoomRepository chatRoomRepository;
    private final AuthRepository authRepository;
    private User admin ;

    public ChatListViewModel() {
       this.chatRooms = new MutableLiveData<>(new ArrayList<>());
       chatRoomRepository = new ChatRoomRepositoryImpl();
       authRepository = new AuthRepositoryImpl();
       admin = sessionManager.getAdmin();
    }

    private  void loadChatRooms(){
        chatRoomRepository.listenAll((querySnapshot,e) ->{
            if (e != null) {
                Log.d(TAG, "Listen failed.", e);
                return;
            }
            if (querySnapshot!=null){
                for (DocumentChange dc : querySnapshot.getDocumentChanges()){
                    switch (dc.getType()){
                        case ADDED:
                            ChatRoom chatRoom = dc.getDocument().toObject(ChatRoom.class);
                            Log.d(TAG, "New chat room: " + chatRoom);
                            for(String id : chatRoom.getMembers()){
                                if(id!= admin.getUserId()){
                                    authRepository.getById(id, task -> {
                                        if(task.isComplete()){
                                            User user = task.getResult().toObject(User.class);
                                            List<ChatListItem> list = chatRooms.getValue();
                                            list.add(new ChatListItem(user,chatRoom));
                                            chatRooms.setValue(list);
                                            Log.d(TAG, "New chat item: " +user);
                                        }
                                    });
                                }
                            }
                            break;
                        case MODIFIED:
                            Log.d(TAG, "Modified chat room: " + dc.getDocument().getData());
                            break;
                    }
                }
            }else{
                Log.d(TAG, "Current data: null");
            }
        });
    }
}
