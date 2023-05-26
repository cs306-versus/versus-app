package com.github.versus;

import com.github.versus.sports.Sport;
import com.github.versus.user.User;

public class SportEquipmentPost {

    //name of the object to sell
    private final String object_name;

    //Sport related to the object
    private final Sport sport;

    //Price of the object
    private final int price;

    //Description of the object
    private final String description;

    //Url of the picture, will be updated in the next Sprint
    private final String url;

    //Dummy picture for now
    private final String picture_local;

    //user that posted this post
    private final User user;

    /**
     * Constructor of the SportEquipmentPost that takes all the informations of the object to display it
     */

    public SportEquipmentPost(String object_name, Sport sport, int price, String description, String url, String picture_local, User user) {
        this.object_name = object_name;
        this.sport = sport;
        this.price = price;
        this.description = description;
        this.url = url;
        this.picture_local = picture_local;
        this.user = user;

    }


    /**
     * Getter for the price
     */
    public int getPrice() {
        return price;
    }

    /**
     * Getter for the object name
     */
    public String getObject_name() {
        return this.object_name;
    }

    /**
     * Getter for the sport of the object
     */
    public Sport getSport() {
        return sport;
    }

    /**
     * Getter for the description of the object
     */
    public String getDescription() {
        return description;
    }



    /**
     * Getter for the user that posted this post
     */
    public User getUser() {
        return user;
    }

    /**
     * Getter for the dummy name of the picture in local
     */
    public String getPicture_local() {
        return picture_local;
    }
}





