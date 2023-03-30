package com.github.versus.sports;

/**
 * Java representation of a sport in Versus
 */

public enum Sport {
    SOCCER("Soccer"),
    ClIMBING("Climbing"),
    FOOTBALL("Football"),
    ROWING("Rowing");


    public final String name;
    private Sport(String name){
        this.name = name;
    }
}
