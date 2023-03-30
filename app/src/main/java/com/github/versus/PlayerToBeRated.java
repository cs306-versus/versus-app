package com.github.versus;

public class PlayerToBeRated {
    private Boolean isRated;
    private String pseudo_name;

    private String rate;
     public Boolean  isPlayerRated(){
         return this.isRated;

     }
     public String get_pseudo_name(){
         return this.pseudo_name;
     }
     public String get_rate(){
         return this.rate;
     }
     public PlayerToBeRated(Boolean isRated,String pseudo_name,String rate){
         this.isRated=isRated;
         this.pseudo_name=pseudo_name;

         this.rate=rate;

     }

}
