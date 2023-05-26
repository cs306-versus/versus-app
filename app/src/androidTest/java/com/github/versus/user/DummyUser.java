package com.github.versus.user;

import com.github.versus.sports.Sport;

import java.util.List;

/**
 * dummmy user used for database manipulation
 * TODO : remove later when actual user implementations are available
 */
public class DummyUser implements User{
    String uid;
    String firstName;
    String lastName;
    String userName;
    String mail;
    String phone;
    int rating;
    String city;
    int zipCode;
    List<Sport> preferredSports;
    public DummyUser(){
        this.uid = "__";
    }
    public DummyUser(String id){
        this.uid = id;
    }
    @Override
    public String getUID() {
        return uid;
    }

    @Override
    public String getFirstName() {
        return null;
    }

    @Override
    public String getLastName() {
        return null;
    }

    @Override
    public String getUserName() {
        return null;
    }

    @Override
    public String getMail() {
        return null;
    }

    @Override
    public String getPhone() {
        return null;
    }

    @Override
    public long getRating() {
        return 0;
    }

    @Override
    public String getCity() {
        return null;
    }

    @Override
    public int getZipCode() {
        return 0;
    }

    @Override
    public List<Sport> getPreferredSports() {
        return null;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof DummyUser)) {
            return false;
        }

        DummyUser other = (DummyUser) obj;
        return this.getUID().equals(other.getUID());
    }
}
