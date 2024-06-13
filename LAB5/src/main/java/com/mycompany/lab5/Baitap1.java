/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.lab5;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author teo
 */
public class Baitap1 {

   public static void main(String[] args) {
        try {
            ExecutorService executor = Executors.newFixedThreadPool(5);
            
            String downloadDir = "C:/Users/teo/Documents/github/advanced-network-programming/LAB5/Dowloaded file/"; // Directory to save files
            Files.createDirectories(Paths.get(downloadDir)); // Create the directory if it doesn't exist
            
            String[] urls = {
                "https://www.gutenberg.org/files/1342/1342-0.txt",
                "https://www.gutenberg.org/files/10/10-0.txt",
                "https://www.gutenberg.org/files/1524/1524-0.txt",
                "https://www.robotstxt.org/robotstxt.html",
                "https://www.gutenberg.org/files/1661/1661-0.txt"
            };
            
            for (String urlString : urls) {
                executor.submit(() -> {
                    try {
                        URL url = new URL(urlString);
                        URLConnection connection = url.openConnection();
                        String fileName = urlString.substring(urlString.lastIndexOf('/') + 1);
                        Path filePath = Paths.get(downloadDir, fileName);
                        
                        InputStream in = connection.getInputStream();
                        FileOutputStream out = new FileOutputStream(filePath.toFile());
                        
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = in.read(buffer)) != -1) {
                            out.write(buffer, 0, bytesRead);
                        }
                        out.close();
                        in.close();
                        System.out.println(Thread.currentThread().getName() + " downloaded: " + fileName + " to " + downloadDir);
                    } catch (IOException e) {
                        System.err.println("Error downloading " + urlString + ": " + e.getMessage());
                    }
                });
            }
            
            executor.shutdown(); // Signal no more tasks
            try {
                executor.awaitTermination(Long.MAX_VALUE, java.util.concurrent.TimeUnit.NANOSECONDS); // Wait for all tasks to complete
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("All downloads complete.");
        } catch (IOException ex) {
            Logger.getLogger(Baitap1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
