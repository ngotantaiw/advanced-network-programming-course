package com.mycompany.lab3.bt2;
import java.io.*;
import java.net.*;

public class PortScannerServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Server listening on port 8080...");

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    System.out.println("Client connected.");

                    String input = in.readLine();
                    String[] parts = input.split(" ");

                    if (parts.length != 2) {
                        out.println("ERROR: Invalid input format. Please use 'host port'");
                        continue;
                    }

                    String host = parts[0];
                    int maxPort;
                    try {
                        maxPort = Integer.parseInt(parts[1]);
                    } catch (NumberFormatException e) {
                        out.println("ERROR: Invalid port number");
                        continue;
                    }

                    out.println("Scanning ports on " + host + " from 1 to " + maxPort);

                    for (int port = 1; port <= maxPort; port++) {
                        try (Socket portSocket = new Socket(host, port)) {
                            out.println("Port " + port + " is open");
                        } catch (IOException e) {
                            out.println("Port " + port + " is closed"); // Print closed ports
                        }
                    }

                    out.println("Scan complete.");
                    System.out.println("Client disconnected.");
                } catch (IOException e) {
                    System.err.println("Error handling client: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }
}

