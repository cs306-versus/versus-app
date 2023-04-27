package com.github.versus.rating;

import com.github.versus.user.User;

public class Rating {
    public static final int DEFAULT_ELO = 1200;
    public static final int MAX_ELO = 5000;
    public static final int MIN_ELO = 200;

    private final int ELOCONSTANT = 32;

    public static final int MIN_RATING = 1 ;
    public static final int MAX_RATING = 5;
    private final User rater;
    private final User rated;
    private final int rating;

    /**
     * main constructor for Rating class, represents a rating from user "rater" to a user "rated"
     * @param rater
     * @param rated
     * @param rating hasw to be between MIN_RATING and MAX_RATING otherwise throws IllegalArgumentException
     *
     */
    public Rating(User rater, User rated, int rating){
        if(rating < MIN_RATING || rating > MAX_RATING  ) throw new IllegalArgumentException();
        this.rater = rater;
        this.rated = rated;
        this.rating = clampElo(MIN_RATING, MAX_RATING, rating);

    }

    /**
     *
     * @return the Rater user
     */
    public User getRater() {
        return rater;
    }
    /**
     *
     * @return the Rated user
     */
    public User getRated() {
        return rated;
    }

    /**
     *
     * @return the new Elo of the rated player after this rating's application
     */
    public int computeNewRating() {
        float centeredElo = rating - (MAX_RATING + MIN_RATING /2);
        int newElo = (int)(getRater().getRating() + ELOCONSTANT*(centeredElo/5));
        return clampElo(newElo,MIN_ELO, MAX_ELO);
    }
    private static int clampElo(int elo, int minVal,int maxVal){
        return Math.max(MIN_ELO, Math.min(elo, MAX_ELO));
    }

}
