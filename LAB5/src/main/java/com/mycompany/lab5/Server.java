/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab5;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class Server {
    public static void main(String[] args) {
        int port = 12345;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started, waiting for connections...");

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
                     ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream())) {

                    System.out.println("Client connected");

                    // Receive the search string and file list from the client
                    String searchString = (String) ois.readObject();
                    List<String> filePaths = (List<String>) ois.readObject();

                    // Prepare results
                    Map<String, Integer> results = new HashMap<>();

                    // Check each file for the search string
                    for (String filePath : filePaths) {
                        int count = searchFile(filePath, searchString);
                        if (count > 0) {
                            results.put(filePath, count);
                        }
                    }

                    // Send results back to the client
                    oos.writeObject(results);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int searchFile(String filePath, String searchString) {
        int count = 0;
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) {
                int index = 0;
                while ((index = line.indexOf(searchString, index)) != -1) {
                    count++;
                    index += searchString.length();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }
}
