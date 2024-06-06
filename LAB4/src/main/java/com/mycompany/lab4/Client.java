package com.mycompany.lab4;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 8080);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            Scanner scanner = new Scanner(System.in);
            String userInput;

            while (true) {
                System.out.println("\nMenu:");
                System.out.println("1. Send URL to download");
                System.out.println("2. Dowload HTML");
                System.out.println("3. Get server name");
                System.out.println("4. Exit");

                userInput = scanner.nextLine();

                switch (userInput) {
                    case "1":
                        out.println("DOWNLOAD_FILE"); // Tell server to expect a URL
                        System.out.print("Enter the URL to download: ");
                        userInput = scanner.nextLine();
                        out.println(userInput);
                        System.out.println("Server: " + in.readLine());
                        break;
                    case "2":
                        out.println("DOWLOAD_HTML");
                        System.out.println("Enter URL to dowload HTML file:");
                        userInput = scanner.nextLine();
                        out.println(userInput);
                        break;
                    case "3":
                        out.println("WHOIS_LOOKUP");
                        System.out.println("Enter domain:");
                        userInput = scanner.nextLine();
                        out.println(userInput);
                        break;
                    case "4":
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice");
                }
            }

        } catch (UnknownHostException e) {
            System.err.println("Unknown host");
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
    }
    
}
