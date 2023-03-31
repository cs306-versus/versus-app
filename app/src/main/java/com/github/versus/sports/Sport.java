package com.github.versus.sports;

/**
 * Java representation of a sport in Versus
 * TODO: implement this for language support: https://stackoverflow.com/questions/57987551/how-to-create-multi-language-enum-in-android
 */

public enum Sport {
    ARCHERY("Archery"),
    BADMINTON("Badminton"),
    BASEBALL("Baseball"),
    BASKETBALL("Basketball"),
    BOWLING("Bowling"),
    BOXING("Boxing"),
    CANOEING("Canoeing"),
    CLIMBING("Climbing"),
    CRICKET("Cricket"),
    CURLING("Curling"),
    CYCLING("Cycling"),
    FENCING("Fencing"),
    FOOTBALL("Football"),
    GOLF("Golf"),
    GYMNASTICS("Gymnastics"),
    HANDBALL("Handball"),
    HOCKEY("Hockey"),
    JUDO("Judo"),
    KAYAKING("Kayaking"),
    LACROSSE("Lacrosse"),
    MARTIALARTS("Martial Arts"),
    POWERLIFTING("Powerlifting"),
    RACQUETBALL("Racquetball"),
    ROCKCLIMBING("Rock Climbing"),
    RUGBY("Rugby"),
    SKIING("Skiing"),
    SNOWBOARDING("Snowboarding"),
    SOCCER("Soccer"),
    SQUASH("Squash"),
    STRONGMAN("Strongman"),
    SURFING("Surfing"),
    SWIMMING("Swimming"),
    TABLETENNIS("Table Tennis"),
    TAEKWONDO("Taekwondo"),
    TENNIS("Tennis"),
    TRACKANDFIELD("Track and Field"),
    ULTIMATEFRISBEE("Ultimate Frisbee"),
    VOLLEYBALL("Volleyball"),
    WATERPOLO("Water Polo"),
    WEIGHTLIFTING("Weightlifting"),
    WRESTLING("Wrestling");





    public final String name;
    private Sport(String name){
        this.name = name;
    }
}
