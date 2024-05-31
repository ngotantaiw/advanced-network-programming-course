package com.mycompany.lab3;
import java.net.*;
import java.io.*;

public class LAB3 {

public static void main(String[] args) {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(9876);
            byte[] receiveData = new byte[1024];
            byte[] sendData;

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);
                String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

                if (message.equalsIgnoreCase("close")) {
                    System.out.println("Client requested to close the connection.");
                    break;
                }

                int number = Integer.parseInt(message.trim());
                System.out.println("Received number: " + number);

                StringBuilder countdownMessage = new StringBuilder();
                for (int i = number; i >= 0; i--) {
                    countdownMessage.append(i).append(" ");
                }

                sendData = countdownMessage.toString().trim().getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                socket.send(sendPacket);
                System.out.println("Countdown sent: " + countdownMessage.toString().trim());
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