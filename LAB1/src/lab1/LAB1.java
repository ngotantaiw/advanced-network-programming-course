/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package lab1;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 *
 * @author teo
 */
public class LAB1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BT1();
        BT2();
        BT3();
        }
    public static void BT1 (){
        Student student = new Student ("Ngo Tan Tai",10);
        Scanner scanner = new Scanner (System.in);
        System.out.println("NHap diem:");
        
        while (true){
            int score = scanner.nextInt();
            if (score < 0){
                break;
            }
            System.out.println("Ban da nhap: "+ score);
            student.addScore(score);
            
        }
        System.out.println("\nSau khi them diem");
        System.out.println(student);
    }
    public static void BT3(){
        CheckSubdomain checkSubdomain = new CheckSubdomain();
        checkSubdomain.setDomain("huflit.edu.vn");
        checkSubdomain.setSubdomains("d:/subdomain.txt");
        checkSubdomain.check();
    }
    public static void BT2() {
        String domain = "example.com"; // Thay thế bằng tên miền cần lấy địa chỉ IP

        try {
            InetAddress address = InetAddress.getByName(domain);

            System.out.println("IP Address for " + domain + ": " + address.getHostAddress());
        } catch (UnknownHostException e) {
            System.out.println("Unknown host: " + domain);
        }
    }
    }
    

