package com.github.versus.rating;

import com.github.versus.user.User;

public class Rating {
    public static final int DEFAULT_ELO = 1200;
    public static final int MAX_ELO = 5000;
    public static final int Min_ELO = 200;

    private final int ELOCONSTANT = 32;

    public static final int MAX_RATING = 1 ;
    public static final int MIN_RATING = 5;
    private final User rater;
    private final User rated;
    private final float rating;

    public Rating(User rater, User rated, float rating){
        this.rater = rater;
        this.rated = rated;
        this.rating = rating;

    }

    public User getRater() {
        return rater;
    }

    public User getRated() {
        return rated;
    }

    public float ComputeNewRating() {
        float centeredRating = rating - (MAX_RATING + MIN_RATING /2);
        int newElo = (int)(getRater().getRating() + ELOCONSTANT*(centeredRating/5));
        return clampElo(newElo,Min_ELO, MAX_ELO);
    }
    private static int clampElo(int elo, int minVal,int maxVal){
        return Math.max(Min_ELO, Math.min(elo, MAX_ELO));
    }

}
