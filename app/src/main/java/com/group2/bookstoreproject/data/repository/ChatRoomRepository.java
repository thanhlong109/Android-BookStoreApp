package com.group2.bookstoreproject.data.repository;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;
import com.group2.bookstoreproject.data.model.ChatMessage;
import com.group2.bookstoreproject.data.model.ChatRoom;

public interface ChatRoomRepository extends BaseRepository<ChatRoom>{
    void getChatRoomsByMember(String memberId, OnCompleteListener<QuerySnapshot> onCompleteListener);
    ListenerRegistration listenToChatRoomsByMember(String memberId, EventListener<QuerySnapshot> eventListener);

    void addMessageToChatRoom(String chatRoomId, ChatMessage message, OnCompleteListener<DocumentReference> onCompleteListener);
    ListenerRegistration listenToMessages(String chatRoomId, EventListener<QuerySnapshot> eventListener);
}
