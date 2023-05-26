package com.github.versus.user;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.versus.sports.Sport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Implementation of a User for Versus
 *
 * @author Hamza REMMAL (hamza.remmal@epfl.ch)
 * @since SPRINT 2
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public final class VersusUser implements User, Serializable {

    private final String uid;
    private final String firstName;
    private final String lastName;
    private final String userName;
    private final String mail;
    private final String phone;
    private final long rating;
    private final String city;
    private final int zip;
    private final List<Sport> preferredSports;

    private final List<String> friends;


    public VersusUser(){
        city = "";
        preferredSports =  new ArrayList<>();
        zip = -1;
        rating = 0;
        phone = "";
        mail = "";
        userName = "";
        uid = "";
        firstName = "";
        lastName = "";
        friends = new ArrayList<>();
    }
    public VersusUser(String uid, String firstName,String lastName,String userName, String mail, String phone , long rating , String city, int zipCode,List<Sport> preferredSports, List<String> friends ){
        this.uid = uid;
        this.firstName =firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.mail =mail;
        this.phone = phone;
        this.rating =rating;
        this.city = city;
        this.zip =zipCode;
        this.preferredSports = preferredSports;
        this.friends = friends;
    }
    public VersusUser(VersusBuilder builder){
        this.uid = builder.uid;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.userName = builder.userName;
        this.mail = builder.mail;
        this.phone = builder.phone;
        this.rating = builder.rating;
        this.city = builder.city;
        this.zip = builder.zipCode;
        this.preferredSports = builder.preferredSports ==  null ? new ArrayList<>() :List.copyOf(builder.preferredSports);
        this.friends = builder.friends ==  null ? new ArrayList<>() :List.copyOf(builder.friends);
    }

    @Override
    public String getUID() {
        return uid;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public String getMail() {
        return mail;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public long getRating() {
        return rating;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public int getZipCode() {
        return zip;
    }

    @Override
    public List<Sport> getPreferredSports() {
        return preferredSports;
    }

    public List<String> getFriends() {
        return friends;
    }


    public Map<String, Object> getAllAttributes(){
        Map<String, Object> fields = new HashMap<>();

        // Add All fields
        fields.put("first-name", getFirstName());
        fields.put("last-name", getLastName());
        fields.put("uid", getUID());
        fields.put("username", getUserName());
        fields.put("mail", getMail());
        fields.put("phone", getPhone());
        fields.put("rating", getRating());
        fields.put("city", getCity());
        fields.put("zip", getZipCode());
        fields.put("preferred-sports", getPreferredSports());
        fields.put("friends", getFriends());

        return fields;
    }
    @Override
    public int hashCode() {
        return Objects.hashCode(uid);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return (obj instanceof VersusUser) && uid.equals(((VersusUser) obj).uid);
    }

    public static String computeUID(String mail){
        String uid =  Integer.toHexString(mail.hashCode()).toUpperCase();
        return uid;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("[User %s - %s]", uid, userName);
    }

    /**
     * Builder class to build new Versus User
     *
     * @author Hamza REMMAL (hamza.remmal@epfl.ch)
     * @since SPRINT 2
     */
    public static final class VersusBuilder extends Builder{

        private String uid;
        private String firstName;
        private String lastName;
        private String userName;
        private String mail;
        private String phone;
        private long rating;
        private String city;
        private int zipCode;
        private List<Sport> preferredSports = new ArrayList<>();

        private List<String> friends = new ArrayList<>();

        /**
         * ???
         * @param uid
         */
        public VersusBuilder(String uid){
            this.uid = uid;
        }

        /**
         * ???
         * @param firstName
         * @return
         */
        @Override
        public VersusBuilder setFirstName(String firstName){
            this.firstName = firstName;
            return this;
        }

        /**
         * ???
         * @param lastName
         * @return
         */
        @Override
        public VersusBuilder setLastName(String lastName){
            this.lastName = lastName;
            return this;
        }

        /**
         * ???
         * @param userName
         * @return
         */
        @Override
        public VersusBuilder setUserName(String userName){
            this.userName = userName;
            return this;
        }

        /**
         * ???
         * @param mail
         * @return
         */
        @Override
        public VersusBuilder setMail(String mail){
            this.mail = mail;
            return this;
        }

        /**
         * ???
         * @param phone
         * @return
         */
        @Override
        public VersusBuilder setPhone(String phone){
            this.phone = phone;
            return this;
        }

        @Override
        public VersusBuilder setRating(long rating) {
            this.rating = rating;
            return this;
        }



        /**
         * ???
         * @param rating
         * @return
         */


        /**
         * ???
         * @param city
         * @return
         */
        @Override
        public VersusBuilder setCity(String city){
            this.city = city;
            return this;
        }

        /**
         * ???
         * @param zip
         * @return
         */
        @Override
        public VersusBuilder setZipCode(int zip){
            this.zipCode = zip;
            return this;
        }

        /**
         * ???
         * @param sports
         * @return
         */
        @Override
        public VersusBuilder setPreferredSports(List<Sport> sports){
            this.preferredSports = sports;
            return this;
        }

        public Builder setFriends(List<String> friends){
            this.friends = friends;
            return this;
        }

        public Builder addFriend(String friendUID){
            friends.add(friendUID);
            return this;
        }

        @Override
        public VersusBuilder setUID(String uid){
            this.uid = uid;
            return this;
        }

        /**
         * ???
         * @return
         */
        @Override
        public VersusUser build(){
            return new VersusUser(this);
        }

    }

}