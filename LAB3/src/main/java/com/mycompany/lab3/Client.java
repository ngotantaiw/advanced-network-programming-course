package com.mycompany.lab3;
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {

 public static void main(String[] args) {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName("localhost");
            byte[] sendData;
            byte[] receiveData = new byte[1024];
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.print("Enter a number to start countdown or 'close' to disconnect: ");
                String message = scanner.nextLine();

                if (message.equalsIgnoreCase("close")) {
                    sendData = message.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, 9876);
                    socket.send(sendPacket);
                    break;
                }

                try {
                    int number = Integer.parseInt(message);
                    sendData = message.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, 9876);
                    socket.send(sendPacket);

                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    socket.receive(receivePacket);
                    String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
                    System.out.println("FROM SERVER: " + response);
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid number or 'close' to disconnect.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }
}