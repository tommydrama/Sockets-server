package com.sda.chat.api;

import com.sda.chat.domain.ChatService;
import com.sda.chat.domain.model.ChatUser;
import com.sda.chat.domain.port.UsersRepository;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatConnectionTask implements Runnable {
    private Socket socket;
    private Scanner in;
    private PrintWriter out;
    private UsersRepository usersRepository;
    private ChatService chatService;

    public ChatConnectionTask(Socket socket, UsersRepository usersRepository, ChatService chatService) throws IOException {
        this.socket = socket;
        this.in = new Scanner(socket.getInputStream());
        this.out = new PrintWriter(socket.getOutputStream());
        this.usersRepository = usersRepository;
        this.chatService = chatService;
    }

    @Override
    public void run() {
        try{
            ChatUser user = authenticate();
            boolean flag = true;
            while(flag){
            String message = in.nextLine();
            String response = chatService.handleMessage(message, user);
            out.println(response);
            out.flush();
            }



        }catch (Exception e){

        }finally {
            try{
                in.close();
                out.close();
                socket.close();
            }catch (IOException e){

            }
        }

    }

    private ChatUser authenticate() {
        String adress = socket.getInetAddress().getHostAddress();
        String possibleName = in.nextLine();
        String[] splitMessage = possibleName.split(" ");
        if (!"hello".equals(splitMessage[0])) {
            throw new InvalidCommandException("Invalid Comand: " + splitMessage[0]);
        }
        String name = splitMessage[1];
        ChatUser chatUser = usersRepository.find(adress);
        if(chatUser == null){
            return usersRepository.addUser(new ChatUser(name, adress));
        }else {
            if (chatUser.getName().equalsIgnoreCase()){
                return chatUser;
            }else {
                throw new InvalidChatUserException("Invalid name: "+name);
            }
        }
    }
}