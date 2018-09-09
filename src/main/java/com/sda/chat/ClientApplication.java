package com.sda.chat;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientApplication {
    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("localhost", 8082);
        OutputStream outputStream = socket.getOutputStream();
        PrintWriter printWriter = new PrintWriter(outputStream);
        printWriter.println("hello Tomek");
        printWriter.flush();

        printWriter.println("users");
        printWriter.flush();

        Scanner scanner = new Scanner(socket.getInputStream());
        String names = scanner.nextLine();
        System.out.println(names);

        socket.close();
    }
}