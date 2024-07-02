package com.group2.bookstoreproject.data.repositoryImpl;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.group2.bookstoreproject.data.model.ChatMessage;
import com.group2.bookstoreproject.data.model.ChatRoom;
import com.group2.bookstoreproject.data.repository.ChatRoomRepository;

public class ChatRoomRepositoryImpl extends BaseRepositoryImpl<ChatRoom> implements ChatRoomRepository {

    private static final String COLLECTION_PATH = "chatRooms";
    @Override
    protected String getCollectionPath() {
        return COLLECTION_PATH;
    }

    @Override
    public void getChatRoomsByMember(String memberId, OnCompleteListener<QuerySnapshot> onCompleteListener) {
        Query query = getCollection(getCollectionPath()).whereArrayContains("members", memberId);
        query(query, onCompleteListener);
    }

    @Override
    public ListenerRegistration listenToChatRoomsByMember(String memberId, EventListener<QuerySnapshot> eventListener) {
        Query query = getCollection(getCollectionPath()).whereArrayContains("members", memberId);
        return listenQuery(query, eventListener);
    }


    @Override
    public void addMessageToChatRoom(String chatRoomId, ChatMessage message, OnCompleteListener<DocumentReference> onCompleteListener) {
        getCollection(getCollectionPath())
                .document(chatRoomId)
                .collection("messages")
                .add(message)
                .addOnCompleteListener(onCompleteListener);

        getCollection(getCollectionPath())
                .document(chatRoomId)
                .update("lastMessage", message.getMessageContent(), "lastActiveTime", message.getSendTime());
    }

    @Override
    public void setChatRoomSeen(String chatRoomId, boolean isSeen, OnCompleteListener<DocumentReference> onCompleteListener) {
        getCollection(getCollectionPath())
                .document(chatRoomId)
                .update("chatSeen", isSeen);
    }

    @Override
    public ListenerRegistration listenToMessages(String chatRoomId, EventListener<QuerySnapshot> eventListener) {
        return getCollection(getCollectionPath())
                .document(chatRoomId)
                .collection("messages")
                .orderBy("sendTime", Query.Direction.ASCENDING)
                .addSnapshotListener(eventListener);
    }
}
