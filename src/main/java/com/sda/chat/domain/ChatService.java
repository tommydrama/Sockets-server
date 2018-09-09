package com.sda.chat.domain;

import com.sda.chat.api.InvalidChatUserException;
import com.sda.chat.api.InvalidCommandException;
import com.sda.chat.domain.model.ChatCommand;
import com.sda.chat.domain.model.ChatUser;
import com.sda.chat.domain.port.UsersRepository;

import java.util.List;

public class ChatService {

    private UsersRepository usersRepository;

    public ChatService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public ChatUser authenticate(String message, String adress){
        String[] splitMessage = message.split(" ");
        if (!"hello".equals(splitMessage[0])) {
            throw new InvalidCommandException("Invalid command: " + splitMessage[0]);
        }
        String name = splitMessage[1];

        ChatUser chatUser = usersRepository.find(adress);
        if(chatUser == null){
            return usersRepository.addUser(new ChatUser(name, adress));
        }else {
            if (chatUser.getName().equalsIgnoreCase(name)){
                return chatUser;
            }else {
                throw new InvalidChatUserException("Invalid name: "+name);
            }
        }
    }


    public String handleMessage(String message, ChatUser chatUser){
        String[] splitMessage = message.split(";");
        String command = splitMessage[0];

        ChatCommand chatCommand = ChatCommand.getByCommandName(command);

        switch (chatCommand){
            case LIST_USERS:
                List<ChatUser> users = usersRepository.findAll();
                StringBuilder stringBuilder = new StringBuilder();
                for (ChatUser user: users){
                    stringBuilder.append(";").append(user.getName());

                }return stringBuilder.substring(1);
                default:
                    return "";



        }
    }
}
