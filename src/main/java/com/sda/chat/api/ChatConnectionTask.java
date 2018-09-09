package com.sda.chat.api;

import com.sda.chat.domain.ChatService;
import com.sda.chat.domain.model.ChatUser;
import com.sda.chat.domain.port.UsersRepository;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ChatConnectionTask implements Runnable {
    private Socket socket;
    private Scanner in;
    private PrintWriter out;
    private ChatService chatService;

    public ChatConnectionTask(Socket socket, ChatService chatService) throws IOException {
        this.socket = socket;
        this.in = new Scanner(socket.getInputStream());
        this.out = new PrintWriter(socket.getOutputStream());
        this.chatService = chatService;
    }

    @Override
    public void run() {
        ChatUser user = null;
        try {
            System.out.println("Started new thread");
            user = authenticate();
            boolean flag = true;
            while (flag) {
                String message = in.nextLine();
                System.out.println("Received message from " + user.getName() + ": " + message);
                String response = chatService.handleMessage(message, user);
                System.out.println("Sending response " + response);
                out.println(response);
                out.flush();
            }
        } catch (NoSuchElementException e) {
            if (user != null) {
                System.out.println(user.getName() + " disconnected");
            } else System.out.println("Someone disconnected");


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException e) {

            }
        }
    }

    private ChatUser authenticate() {
        String adress = socket.getInetAddress().getHostAddress();
        String message = in.nextLine() + adress;
        return chatService.authenticate(message, null);
    }
}