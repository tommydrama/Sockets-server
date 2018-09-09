package com.sda.chat.infrastructure.memory;

import com.sda.chat.domain.model.ChatMessage;
import com.sda.chat.domain.model.ChatUser;
import com.sda.chat.domain.port.MessagesRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class InMemoryMessagesRepository implements MessagesRepository {
    private List<ChatMessage> chatMessageList;

    public InMemoryMessagesRepository() {
        this.chatMessageList = new ArrayList <>();
    }


    @Override
    public void saveMessage(ChatMessage message) {

    chatMessageList.add(message) ;   }

    @Override
    public List <ChatMessage> findMessagesFor(ChatUser chatUser) {
//        List<ChatMessage> messages = new ArrayList <>();
//        for (ChatMessage message : this.chatMessageList){
//            if(message.getSender().equals(chatUser) || message.getReceiver().equals(chatUser)){
//                messages.add(message);
//            }
//        }
        return this.chatMessageList.
                stream().
                filter(e-> e.getSender().equals(chatUser) ||
                        e.getReceiver().equals(chatUser)).
                collect(Collectors.toList());
    }
}
