package com.mycompany.lab3.bt2;
import java.io.*;
import java.net.*;

public class PortScannerClient {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int port = 8080;

        try (Socket socket = new Socket(serverAddress, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connected to server.");

            System.out.print("Enter host and port (e.g., 'localhost 443'): ");
            String userInput = stdIn.readLine();
            out.println(userInput);  // Send the combined input

            String response;
            while ((response = in.readLine()) != null) {
                System.out.println(response);
            }
        } catch (IOException e) {
            System.err.println("Client error: " + e.getMessage());
        }
    }
}

