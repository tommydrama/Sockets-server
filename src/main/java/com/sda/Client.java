package com.sda;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("192.168.100.119", 8081);
        Scanner consoleScanner = new Scanner(System.in);
        String name = consoleScanner.nextLine();
        String message = consoleScanner.nextLine();

        OutputStream outputStream = socket.getOutputStream();
        PrintWriter output = new PrintWriter(new OutputStreamWriter(outputStream));

        output.println(name);
        output.println(message);
        output.flush();

        Scanner scanner = new Scanner(socket.getInputStream());
        String next = scanner.nextLine();
        System.out.println(next);

        socket.close();

    }
}
