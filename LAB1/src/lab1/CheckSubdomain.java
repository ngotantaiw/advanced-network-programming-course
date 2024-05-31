/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab1;

import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 *
 * @author teo
 */
public class CheckSubdomain {
    private String domain;
    private String subdomain;
    public CheckSubdomain(){
        this.domain ="google.com";
        this.subdomain="";
        
    }
    public void setDomain (String domain){
        this.domain = domain;
    }
    public void setSubdomains (String subdomain){
        this.subdomain = subdomain;
    }
    public void check(){
        try {
            List<String> allLines = Files.readAllLines(Paths.get(this.subdomain));
            try(FileWriter myWriter = new FileWriter("D:/result.txt")){
                for (String line : allLines){
                    try{
                        InetAddress address = InetAddress.getByName(line + "."+ this.domain);
                        System.out.println(line + "\n");
                        if (address.isReachable(10000)){
                            System.out.println(address + "=> is reachable");
                            myWriter.write(address.toString()+ "\n");
                        }
                    } catch (IOException e){
                        System.out.println("loi: "+ e.getMessage());
                    }
                }
            }
            
        } catch (IOException e){
            System.out.println("");
        }
    }
}
