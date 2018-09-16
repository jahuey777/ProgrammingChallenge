package com.jaimejahuey.programmingchallenge.model;

import java.io.Serializable;
import java.util.ArrayList;

import io.realm.annotations.PrimaryKey;

public class ProfileInformation implements Serializable {

    @PrimaryKey private String key;
    private String gender;
    private String name;
    private int age;
    private String imageUrl;
    private String hobbies;

    public ProfileInformation() {}

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

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ProfileInformation copyProfile(ProfileInformation profile) {
        ProfileInformation profileInformation = new ProfileInformation();
        profileInformation.name = profile.name;
        profileInformation.age = profile.age;
        profileInformation.hobbies = profile.hobbies;

        return profileInformation;
    }

    public boolean sameHobbies(ProfileInformation profile){
        return this.getHobbies().equals(profile.getHobbies());
    }

}
