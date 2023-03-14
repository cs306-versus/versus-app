package com.github.versus.user;

import com.github.versus.sports.Sport;

import java.util.List;

/**
 * dummmy user used for database manipulation
 * TODO : remove later when actual user implementations are available
 */
public class DummyUser implements User{
    private String id;
    public DummyUser(String id){
        this.id = id;
    }
    @Override
    public String getUID() {
        return id;
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
    public int getRating() {
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
}
