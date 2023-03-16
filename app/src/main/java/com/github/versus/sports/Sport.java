package com.github.versus.sports;

/**
 * Java representation of a sport in Versus
 */
public enum Sport {
    // TODO : Add all the
    SOCCER("Soccer"),
    ClIMBING("Climbing"),
    ROWING("Rowing");

    public final String name;
    private Sport(String name){
        this.name = name;
    }
}
