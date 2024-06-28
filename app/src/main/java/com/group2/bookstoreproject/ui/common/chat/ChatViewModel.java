package com.group2.bookstoreproject.ui.common.chat;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.group2.bookstoreproject.base.BaseViewModel;
import com.group2.bookstoreproject.data.model.ChatMessage;
import com.group2.bookstoreproject.data.model.ChatRoom;
import com.group2.bookstoreproject.data.model.User;
import com.group2.bookstoreproject.data.model.base.Resource;
import com.group2.bookstoreproject.data.repository.ChatRoomRepository;
import com.group2.bookstoreproject.data.repositoryImpl.ChatRoomRepositoryImpl;
import com.group2.bookstoreproject.util.session.SessionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

public class ChatViewModel extends BaseViewModel {
    public static final String TAG = "Chat view model";
    private MutableLiveData<List<ChatMessage>> chatMessages;
    private ChatRoomRepository chatRoomRepository;

    private MutableLiveData<Resource<ChatRoom>> currentChatRoom;
    private ListenerRegistration listenToMessagesInChatRoom;

    public MutableLiveData<List<ChatMessage>> getChatMessages() {
        return chatMessages;
    }

    User sender;
    User receiver;

    public MutableLiveData<Resource<ChatRoom>> getCurrentChatRoom() {
        return currentChatRoom;
    }

    public void setCurrentChatRoom(MutableLiveData<Resource<ChatRoom>> currentChatRoom) {
        this.currentChatRoom = currentChatRoom;
    }


    public ChatViewModel() {
        chatRoomRepository = new ChatRoomRepositoryImpl();
        chatMessages = new MutableLiveData<>();
        currentChatRoom = new MutableLiveData<>();
        if(sessionManager.getLoggedInUser().getRole() == 2){
            sender = sessionManager.getLoggedInUser();
            receiver = sessionManager.getAdmin();
            getMyChatRooms();
        }
    }

    public void addChatRoom() {
        setLoading(true);
        List<String> memeberList = new ArrayList<>();
        memeberList.add(sender.getUserId());
        memeberList.add(receiver.getUserId());
        String roomId = UUID.randomUUID().toString();
        ChatRoom cr = new ChatRoom(roomId,false,System.currentTimeMillis(),memeberList);

        chatRoomRepository.upsert(roomId, cr, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    currentChatRoom.setValue(Resource.success(cr));
                }else{
                    setErrorMessage(task.getException().getMessage());
                }
                setLoading(false);
            }
        });
    }

    private void getMyChatRooms(){
        setLoading(true);
        chatRoomRepository.getChatRoomsByMember(sender.getUserId(),task -> {
            if(task.isSuccessful()){
                QuerySnapshot querySnapshot = task.getResult();
                if(querySnapshot.size()==0){
                    Log.d(TAG, "Not found");
                    currentChatRoom.setValue(Resource.error("Not found", null));
                    setLoading(false);
                    return;
                }
                    ChatRoom chatRoom = querySnapshot.getDocuments().get(0).toObject(ChatRoom.class);
                    Log.d(TAG, "ChatRoom ID: " + chatRoom.getChatRoomId());
                    currentChatRoom.setValue(Resource.success(chatRoom));
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
//        chatRoomRepository.listenToChatRoomsByMember(senderId,(querySnapshot, e) -> {
//            if (e != null) {
//                Log.d(TAG, "Listen failed.", e);
//                return;
//            }
//            if (querySnapshot != null) {
//                for (DocumentChange dc : querySnapshot.getDocumentChanges()) {
//                    switch (dc.getType()) {
//                        case ADDED:
//                            Log.d(TAG, "New chat room: " + dc.getDocument().getData());
//                            break;
//                        case MODIFIED:
//                            Log.d(TAG, "Modified chat room: " + dc.getDocument().getData());
//                            break;
//                    }
//                }
//            } else {
//                Log.d(TAG, "Current data: null");
//            }
//        });
    }

    public void addMessageToChatRoom( String text) {
        String chatRoomId = currentChatRoom.getValue().getData().getChatRoomId();
        setLoading(true);
        ChatMessage message = new ChatMessage();
        message.setSendTime(System.currentTimeMillis());
        message.setMessageContent(text);
        message.setChatRoomId(chatRoomId);
        message.setSeen(false);
        message.setSenderId(sender.getUserId());
        message.setReceiverId(receiver.getUserId());

        chatRoomRepository.addMessageToChatRoom(chatRoomId, message, task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, "Message added successfully.");
            } else {
                Log.d(TAG, "Error adding message: ", task.getException());
                setErrorMessage(task.getException().getMessage());
            }
            setLoading(false);
        });
    }

    public void listenToMessagesInChatRoom(String chatRoomId) {
        Log.d(TAG, "add message: "+chatRoomId);
        listenToMessagesInChatRoom = chatRoomRepository.listenToMessages(chatRoomId, (querySnapshot, e) -> {
            if (e != null) {
                Log.d(TAG, "Listen failed.", e);
                return;
            }
            if (querySnapshot != null) {
                for (DocumentChange dc : querySnapshot.getDocumentChanges()) {
                    switch (dc.getType()) {
                        case ADDED:

                            ChatMessage newMessage = dc.getDocument().toObject(ChatMessage.class);
                            if(chatMessages.getValue() == null){
                                List<ChatMessage> list = new ArrayList<>();
                                list.add(newMessage);
                                chatMessages.setValue(list);
                            }else{
                                boolean exists = false;
                                for (ChatMessage message : chatMessages.getValue()) {
                                    if (message.getSendTime() == newMessage.getSendTime()) {
                                        exists = true;
                                        break;
                                    }
                                }
                                if (!exists) {
                                    List<ChatMessage> list = chatMessages.getValue();
                                    list.add(newMessage);
                                    chatMessages.setValue(list);
                                }
                            }

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
        if(listenToMessagesInChatRoom !=null){
            listenToMessagesInChatRoom.remove();
        }
    }

}
