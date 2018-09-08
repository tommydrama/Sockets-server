package com.sda;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServerTask implements Runnable {

    private Socket socket;

    public ServerTask(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            Scanner scanner = new Scanner(inputStream);

            String name = scanner.nextLine();
            String message = scanner.nextLine();

            System.out.println("[" + name + "]:" + message);

            OutputStream outputStream = socket.getOutputStream();
            PrintWriter output = new PrintWriter(outputStream);

            output.println("Hello from server");
            output.flush();


        }catch (Exception e) {

        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
