package com.github.versus.user;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.versus.sports.Sport;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Implementation of a User for Versus
 *
 * @author Hamza REMMAL (hamza.remmal@epfl.ch)
 * @since SPRINT 2
 */
public final class VersusUser implements User {

    private final String uid;
    private final String firstName;
    private final String lastName;
    private final String userName;
    private final String mail;
    private final String phone;
    private final int rating;
    private final String city;
    private final int zipCode;
    private final List<Sport> preferredSports;


    private VersusUser(Builder builder){
        this.uid = builder.uid;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.userName = builder.userName;
        this.mail = builder.mail;
        this.phone = builder.phone;
        this.rating = builder.rating;
        this.city = builder.city;
        this.zipCode = builder.zipCode;
        this.preferredSports = List.copyOf(builder.preferredSports);
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
    public int getRating() {
        return rating;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public int getZipCode() {
        return zipCode;
    }

    @Override
    public List<Sport> getPreferredSports() {
        return preferredSports;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uid);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return (obj instanceof VersusUser) && uid.equals(((VersusUser) obj).uid);
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
    public static final class Builder {

        private final String uid;
        private String firstName;
        private String lastName;
        private String userName;
        private String mail;
        private String phone;
        private int rating;
        private String city;
        private int zipCode;
        private List<Sport> preferredSports = new ArrayList<>();

        /**
         * ???
         * @param uid
         */
        public Builder(String uid){
            this.uid = uid;
        }

        /**
         * ???
         * @param firstName
         * @return
         */
        public Builder setFirstName(String firstName){
            this.firstName = firstName;
            return this;
        }

        /**
         * ???
         * @param lastName
         * @return
         */
        public Builder setLastName(String lastName){
            this.lastName = lastName;
            return this;
        }

        /**
         * ???
         * @param userName
         * @return
         */
        public Builder setUserName(String userName){
            this.userName = userName;
            return this;
        }

        /**
         * ???
         * @param mail
         * @return
         */
        public Builder setMail(String mail){
            this.mail = mail;
            return this;
        }

        /**
         * ???
         * @param phone
         * @return
         */
        public Builder setPhone(String phone){
            this.phone = phone;
            return this;
        }

        /**
         * ???
         * @param rating
         * @return
         */
        public Builder setRating(int rating){
            this.rating = rating;
            return this;
        }

        /**
         * ???
         * @param city
         * @return
         */
        public Builder setCity(String city){
            this.city = city;
            return this;
        }

        /**
         * ???
         * @param zip
         * @return
         */
        public Builder setZipCode(int zip){
            this.zipCode = zip;
            return this;
        }

        /**
         * ???
         * @param sports
         * @return
         */
        public Builder setPreferredSports(List<Sport> sports){
            this.preferredSports = sports;
            return this;
        }

        /**
         * ???
         * @return
         */
        public VersusUser build(){
            return new VersusUser(this);
        }

    }

}