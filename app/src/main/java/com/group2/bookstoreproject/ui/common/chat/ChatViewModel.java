package com.group2.bookstoreproject.ui.common.chat;

import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.group2.bookstoreproject.base.BaseViewModel;
import com.group2.bookstoreproject.data.model.ChatMessage;
import com.group2.bookstoreproject.data.model.ChatRoom;
import com.group2.bookstoreproject.data.model.base.Resource;
import com.group2.bookstoreproject.data.repository.ChatRoomRepository;
import com.group2.bookstoreproject.data.repositoryImpl.ChatRoomRepositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChatViewModel extends BaseViewModel {
    public static final String TAG = "Chat view model";
    private MutableLiveData<Resource<String>> AddChatRoomResult;
    private MutableLiveData<List<ChatMessage>> chatMessages;
    private ChatRoomRepository chatRoomRepository;

    private MutableLiveData<ChatRoom> currentChatRoom;
    private ListenerRegistration listenToMessagesInChatRoom;

    String senderId = "thisiSenderId";
    String receiverID2 = "reciverId";

    public MutableLiveData<ChatRoom> getCurrentChatRoom() {
        return currentChatRoom;
    }

    public ChatViewModel() {
        chatRoomRepository = new ChatRoomRepositoryImpl();
        AddChatRoomResult = new MutableLiveData<>();
        //addListener();
        getChatRoomsByMember();
    }

    public void addChatRoom(String receiverID) {
        setLoading(true);
        List<String> memeberList = new ArrayList<>();
        memeberList.add(senderId);
        memeberList.add(receiverID2);
        String roomId = UUID.randomUUID().toString();
        ChatRoom cr = new ChatRoom(roomId,false,System.currentTimeMillis(),memeberList);

        chatRoomRepository.upsert(UUID.randomUUID().toString(), cr, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    AddChatRoomResult.setValue(Resource.success(roomId));
                    currentChatRoom.setValue(cr);
                }else{
                    setErrorMessage(task.getException().getMessage());
                }
                setLoading(false);
            }
        });
    }

    private void getChatRoomsByMember(){
        setLoading(true);
        chatRoomRepository.getChatRoomsByMember(senderId,task -> {
            if(task.isSuccessful()){
                QuerySnapshot querySnapshot = task.getResult();
                if(querySnapshot.size()==0){
                    Log.d(TAG, "Not found");
                    return;
                }
                for (QueryDocumentSnapshot documentSnapshot : querySnapshot){
                    ChatRoom chatRoom = documentSnapshot.toObject(ChatRoom.class);
                    Log.d(TAG, "ChatRoom ID: " + chatRoom.getChatRoomId());
                    currentChatRoom.setValue(chatRoom);
                }
                setLoading(false);
            }else {
                Log.d(TAG, "Error getting documents: ", task.getException());
                setErrorMessage(task.getException().getMessage());
                setLoading(false);
            }
        });
    }

    private void addListener(){
        //listen all
//        chatRoomRepository.listenAll((querySnapshot,e) ->{
//            if (e != null) {
//                Log.d(TAG, "Listen failed.", e);
//                return;
//            }
//            if (querySnapshot!=null){
//                for (DocumentChange dc : querySnapshot.getDocumentChanges()){
//                    switch (dc.getType()){
//                        case ADDED:
//                            Log.d(TAG, "New chat room: " + dc.getDocument().getData());
//                            break;
//                        case MODIFIED:
//                            Log.d(TAG, "Modified chat room: " + dc.getDocument().getData());
//                            break;
//                    }
//                }
//            }else{
//                Log.d(TAG, "Current data: null");
//            }
//        });
        //
        chatRoomRepository.listenToChatRoomsByMember(senderId,(querySnapshot, e) -> {
            if (e != null) {
                Log.d(TAG, "Listen failed.", e);
                return;
            }
            if (querySnapshot != null) {
                for (DocumentChange dc : querySnapshot.getDocumentChanges()) {
                    switch (dc.getType()) {
                        case ADDED:
                            Log.d(TAG, "New chat room: " + dc.getDocument().getData());
                            break;
                        case MODIFIED:
                            Log.d(TAG, "Modified chat room: " + dc.getDocument().getData());
                            break;
                    }
                }
            } else {
                Log.d(TAG, "Current data: null");
            }
        });
    }

    public void addMessageToChatRoom( String text) {
        String chatRoomId = currentChatRoom.getValue().getChatRoomId();
        ChatMessage message = new ChatMessage();
        message.setSendTime(System.currentTimeMillis());
        message.setMessageContent(text);
        message.setChatRoomId(chatRoomId);
        message.setSeen(false);
        message.setSenderId(senderId);
        message.setReceiverId(receiverID2);

        chatRoomRepository.addMessageToChatRoom(chatRoomId, message, task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, "Message added successfully.");
                 List< ChatMessage> list = chatMessages.getValue();
                 list.add(message);
                chatMessages.setValue(list);
            } else {
                Log.d(TAG, "Error adding message: ", task.getException());
            }
        });
    }

    public void listenToMessagesInChatRoom() {
        String chatRoomId = currentChatRoom.getValue().getChatRoomId();
        listenToMessagesInChatRoom = chatRoomRepository.listenToMessages(chatRoomId, (querySnapshot, e) -> {
            if (e != null) {
                Log.d(TAG, "Listen failed.", e);
                return;
            }
            if (querySnapshot != null) {
                for (DocumentChange dc : querySnapshot.getDocumentChanges()) {
                    switch (dc.getType()) {
                        case ADDED:
                            Log.d(TAG, "New message: " + dc.getDocument().getData());
                            break;
                        case MODIFIED:
                            Log.d(TAG, "Modified message: " + dc.getDocument().getData());
                            break;
                    }
                }
            } else {
                Log.d(TAG, "Current data: null");
            }
        });
    }

    public void removeListener(){
        listenToMessagesInChatRoom.remove();
    }

}
