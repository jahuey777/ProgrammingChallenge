package com.jaimejahuey.programmingchallenge.model;

import java.io.Serializable;
import java.util.ArrayList;

import io.realm.annotations.PrimaryKey;

public class ProfileInformation implements Serializable{

    @PrimaryKey private String ID;
    private String gender;
    private String name;
    private int age;
    private String imageUrl;
    private ArrayList<String> hobbies;

    public ProfileInformation() {
    }

    public String getID() {
        return ID;
    }

    public String getGender() {
        return gender;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ArrayList<String> getHobbies() {
        return hobbies;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setHobbies(ArrayList<String> hobbies) {
        this.hobbies = hobbies;
    }

}
