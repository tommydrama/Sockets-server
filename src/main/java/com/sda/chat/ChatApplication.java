package com.sda.chat;

import com.sda.chat.api.ChatServer;
import com.sda.chat.domain.ChatService;
import com.sda.chat.domain.port.UsersRepository;

import com.sda.chat.infrastructure.memory.InMemoryUsersRepository;

import java.io.IOException;

public class ChatApplication {
    public static void main(String[] args) throws IOException {
        UsersRepository usersRepository = new InMemoryUsersRepository();
        ChatService chatService = new ChatService(usersRepository);
        ChatServer chatServer = new ChatServer(chatService);

        chatServer.startServer();
    }
}
