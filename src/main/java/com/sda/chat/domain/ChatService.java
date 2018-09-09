package com.sda.chat.domain;

import com.sda.chat.api.InvalidChatUserException;
import com.sda.chat.api.InvalidCommandException;
import com.sda.chat.domain.model.ChatCommand;
import com.sda.chat.domain.model.ChatMessage;
import com.sda.chat.domain.model.ChatUser;
import com.sda.chat.domain.port.MessagesRepository;
import com.sda.chat.domain.port.UsersRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatService {

    private UsersRepository usersRepository;
    private MessagesRepository messagesRepository;

    public ChatService(UsersRepository usersRepository, MessagesRepository messagesRepository) {
        this.usersRepository = usersRepository;
        this.messagesRepository = messagesRepository;
    }

    public ChatUser authenticate(String message, String adress) {
        String[] splitMessage = message.split(" ");
        if (!"hello".equals(splitMessage[0])) {
            throw new InvalidCommandException("Invalid command: " + splitMessage[0]);
        }
        String name = splitMessage[1];

        ChatUser chatUser = usersRepository.findByAdress(adress);
        if (chatUser == null) {
            return usersRepository.addUser(new ChatUser(name, adress));
        } else {
            if (chatUser.getName().equalsIgnoreCase(name)) {
                return chatUser;
            } else {
                throw new InvalidChatUserException("Invalid name: " + name);
            }
        }
    }

    public String handleMessage(String message, ChatUser chatUser) {
        String[] splitMessage = message.split(" ");
        String command = splitMessage[0];

        ChatCommand chatCommand = ChatCommand.getByCommandName(command);

        switch (chatCommand) {
            case LIST_USERS:
                return handleListUsers();
            case SEND_MESSAGE:
                return handleSendMessage(chatUser, splitMessage);
            case MESSAGES:
                return handleMessages(chatUser, splitMessage);
            default:
                return "";
        }
    }

    private String handleMessages(ChatUser chatUser, String[] splitMessage) {
        if (splitMessage.length == 1) {
            List <ChatMessage> messages =
                    messagesRepository.findMessagesFor(chatUser);
            Map <ChatUser, Integer> map = new HashMap <>();
            for (ChatMessage chatMessage : messages) {
                ChatUser interlocutor = getInterlocutorFor(chatUser, chatMessage);
                if (!map.containsKey(interlocutor)) {
                    map.put(interlocutor, 0);
                }
                Integer currentCounter = map.get(interlocutor);
                currentCounter++;
                map.put(interlocutor, currentCounter);

            }
            return aggregatesToString(map);
        }
        return "";
    }

    private String aggregatesToString(Map <ChatUser, Integer> aggregates) {
//        for(Map.Entry<ChatUser, Integer> entry:aggregates.entrySet()){
//            ChatUser k = entry.getKey();
//            Integer v = entry.getValue();
//        }

        StringBuilder stringBuilder = new StringBuilder();
        aggregates.forEach((k, v) -> {
            stringBuilder.append(";")
                    .append(k.getName())
                    .append(" (")
                    .append(v)
                    .append(") ");

        });
        return stringBuilder.substring(1);
    }

    private ChatUser getInterlocutorFor(ChatUser chatUser, ChatMessage chatMessage) {
        ChatUser sender = chatMessage.getSender();
        ChatUser receiver = chatMessage.getReceiver();
        if (sender.equals(chatUser)) {
            return receiver;
        } else {
            return sender;
        }
    }


    private String handleSendMessage(ChatUser chatUser, String[] splitMessage) {
        ChatUser sender = chatUser;
        String receiverName = splitMessage[1];
        ChatUser receiver = usersRepository.findByName(receiverName);
        String sendMessage = getSendMessageFromMessage(splitMessage);
        ChatMessage chatMessage = new ChatMessage(receiver, sender, sendMessage);
        messagesRepository.saveMessage(chatMessage);
        System.out.println("[" + sender.getName() + "] -> [" + receiver.getName() + "]: \"" + sendMessage + "\"");
        return "message sent";
    }

    private String handleListUsers() {
        List <ChatUser> users = usersRepository.findAll();
        StringBuilder stringBuilder = new StringBuilder();
        for (ChatUser user : users) {
            stringBuilder.append(";").append(user.getName());

        }
        return stringBuilder.substring(1);
    }

    private String getSendMessageFromMessage(String[] splitMessage) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 2; i < splitMessage.length; i++) {
            stringBuilder.append(" ")
                    .append(splitMessage[i]);
        }
        return stringBuilder.substring(1);

    }
}
