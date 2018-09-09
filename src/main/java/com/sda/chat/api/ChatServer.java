package com.sda.chat.api;

import com.sda.chat.domain.ChatService;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

    private ChatService chatService;
    private Boolean isRunning;

    public ChatServer(ChatService chatService) {
        this.chatService = chatService;
    }

    public void startServer() throws IOException {
        isRunning = true;
        ServerSocket serverSocket = new ServerSocket(8082);
        while (isRunning) {
            Socket socket = serverSocket.accept();
            ChatConnectionTask chatConnectionTask = new ChatConnectionTask(socket, chatService);
            new Thread(chatConnectionTask).start();
        }
        serverSocket.close();

    }

}
