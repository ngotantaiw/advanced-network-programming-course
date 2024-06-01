/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.mavenproject1;

/**
 *
 * @author teo
 */
public class Mavenproject1 {

    public static String decrypt(String ciphertext) {
        for (int shift = 1; shift < 26; shift++) {
            String plaintext = "";
            for (char c : ciphertext.toCharArray()) {
                if (Character.isLetter(c)) {
                    char base = Character.isUpperCase(c) ? 'A' : 'a';
                    plaintext += (char) ((c - base + shift) % 26 + base);
                } else {
                    plaintext += c;
                }
            }
            System.out.println("Shift " + shift + ": " + plaintext);
        }
        return ""; // Or return the most likely plaintext based on human inspection
    }

    public static void main(String[] args) {
        String ciphertext = "nouhmuoxoiwhabc";
        decrypt(ciphertext);
    }
}
