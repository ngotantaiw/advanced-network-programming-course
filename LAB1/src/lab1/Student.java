/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab1;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author teo
 */
public class Student {
    private String name;
    private int age;
    private List<Double> scores = new ArrayList<>();
    public Student(String name,int age){
        this.name = name;
        this.age = age;
    }
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public ArrayList<Double> getScores() {
        return new ArrayList<>(scores);
    }

    // Setter methods
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }
    public void addScore(double score){
        scores.add(score);
    }
    public double calculateAverageScore(){
        double sum = 0;
        for (double score : scores){
            sum+= score;
        }
        return sum / scores.size();
    }
    @Override
    public String toString(){
        return "Hoc sinh: "+ name +"\n"+
        "Tuoi: "+ age +"\n"+
        "Diem: "+ scores +"\n"+
        "Trung binh: "+ calculateAverageScore()  +"\n";
    }
        
   }
    
    

