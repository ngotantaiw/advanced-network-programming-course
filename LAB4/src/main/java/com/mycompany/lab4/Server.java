/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.lab4;

import java.io.*;
import java.net.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

public class Server {
    private static final String FILE_DOWNLOAD_DIR = "C:/Users/teo/Documents/github/advanced-network-programming/LAB4/Dowload file/";
    private static final String UrlFileName = "downloaded.html";
    private static final String HTML_DOWNLOAD_DIR = "C:/Users/teo/Documents/github/advanced-network-programming/LAB4/Dowload HTML/";
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8080);
             Socket clientSocket = serverSocket.accept();
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                switch (inputLine) {
                    case "WHOIS_LOOKUP":
                        String domain = in.readLine();
                        WhoisLookup(domain);
                        break;
                    case "DOWLOAD_HTML":
                        String urlHtml = in.readLine();
                        try{
                            URL url = new URL(urlHtml);
                            URLConnection connection = url.openConnection();
                            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
                            // Create file path for saving
                            Path filePath = Paths.get(HTML_DOWNLOAD_DIR, UrlFileName);

                            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toString()));

                            String line;
                            while ((line = reader.readLine()) != null) {
                                writer.write(line);
                                writer.newLine();
                            }
                            reader.close();
                            writer.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "DOWNLOAD_FILE":
                        out.println("Enter the URL to download:");
                        String urlString = in.readLine();
                        try {
                            URL url = new URL(urlString);
                            String fileName = url.getFile().substring(url.getFile().lastIndexOf('/') + 1);
                            String filePath = FILE_DOWNLOAD_DIR + fileName;

                            try (InputStream inStream = url.openStream();
                                 FileOutputStream outStream = new FileOutputStream(filePath)) {

                                byte[] buffer = new byte[4096];
                                int bytesRead;
                                while ((bytesRead = inStream.read(buffer)) != -1) {
                                    outStream.write(buffer, 0, bytesRead);
                                }
                                System.out.println("File downloaded to: " + filePath);
                                out.println("File downloaded successfully!");
                            }
                        } catch (IOException e) {
                            System.err.println("Error downloading file: " + e);
                            out.println("Error downloading file");
                        }
                        break;
                    default:
                        out.println("Invalid command");
                }
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
    }
    public static void WhoisLookup (String domain){
        String whoisServer = "whois.internic.net";
        
        try (Socket socket = new Socket(whoisServer, 43)){
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(domain);
            
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;
            while ((line = in.readLine()) != null){
                System.out.println(line);
            }
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}

