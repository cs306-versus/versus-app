package com.github.versus.user;

import com.github.versus.posts.Post;
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
     * @return (String) - User's UID
     */
    String getUID();

    /**
     * Fetch the User First Name
     * @return  (String) - User's First Name
     */
    String getFirstName();

    /**
     * Fetch the User Last Name
     * @return  (String) - User's Last Name
     */
    String getLastName();

    /**
     * Fetch the User userame
     * @return  (String) - User's username
     */
    String getUserName();

    /**
     * Fetch the User email
     * @return (String) - User's mail
     */
    String getMail();

    /**
     * Fetch the User phone number
     * @return (String) - User's phone number
     */
    String getPhone();

    /**
     * Fetch the User Rating
     * @return (int) - Fetch the User's rating
     */
    int getRating();

    /**
     * Fetch the User's city information
     * @return (String) - User's city
     */
    String getCity();

    /**
     * Fetch the User's zipcode
     * @return (int) - The user's zip code
     */
    int getZipCode();

    /**
     * Fetch the list of preferred sports of the user
     *
     * @return (List<Sport>) - List of preferred sports
     */
    List<Sport> getPreferredSports();


}