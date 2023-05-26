package com.github.versus.user;

import com.github.versus.sports.Sport;

import java.util.List;

/**
 * Abstract Representation of a User in Versus
 *
 * @author Hamza REMMAL (hamza.remmal@epfl.ch)
 * @since 1.0
 */
public interface User {

    /**
     * Fetch the User Unique ID
     *
     * @return (String) - User's UID
     */
    String getUID();

    /**
     * Fetch the User First Name
     *
     * @return (String) - User's First Name
     */
    String getFirstName();

    /**
     * Fetch the User Last Name
     *
     * @return (String) - User's Last Name
     */
    String getLastName();

    /**
     * Fetch the User userame
     *
     * @return (String) - User's username
     */
    String getUserName();

    /**
     * Fetch the User email
     *
     * @return (String) - User's mail
     */
    String getMail();

    /**
     * Fetch the User phone number
     *
     * @return (String) - User's phone number
     */
    String getPhone();

    /**
     * Fetch the User Rating
     *
     * @return (int) - Fetch the User's rating
     */
    long getRating();

    /**
     * Fetch the User's city information
     *
     * @return (String) - User's city
     */
    String getCity();

    /**
     * Fetch the User's zipcode
     *
     * @return (int) - The user's zip code
     */
    int getZipCode();

    /**
     * Fetch the list of preferred sports of the user
     *
     * @return (List < Sport >) - List of preferred sports
     */
    List<Sport> getPreferredSports();

    abstract class Builder {
        /**
         * ???
         * @param firstName
         * @return
         */
        public abstract Builder setFirstName(String firstName);

        /**
         * ???
         * @param lastName
         * @return
         */
        public abstract Builder setLastName(String lastName);

        /**
         * ???
         * @param userName
         * @return
         */
        public abstract Builder setUserName(String userName);

        /**
         * ???
         * @param mail
         * @return
         */
        public abstract Builder setMail(String mail);

        /**
         * ???
         * @param phone
         * @return
         */
        public abstract Builder setPhone(String phone);

        /**
         * ???
         * @param rating
         * @return
         */
        public abstract Builder setRating(long rating);


        /**
         * ???
         * @param city
         * @return
         */
        public abstract Builder setCity(String city);

        /**
         * ???
         * @param zip
         * @return
         */
        public abstract Builder setZipCode(int zip);

        /**
         * ???
         * @param sports
         * @return
         */
        public abstract Builder setPreferredSports(List<Sport> sports);

        public abstract Builder setUID(String uid);

        /**
         * ???
         * @return
         */
        public abstract User build();
    }

}
