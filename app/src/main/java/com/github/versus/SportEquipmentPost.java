package com.github.versus;

import com.github.versus.sports.Sport;
import com.github.versus.user.User;

public class SportEquipmentPost {
    private final String object_name;
    private final Sport sport;
    private final int price ;
    private final String description;
    private final String url ;
    private final String picture_local;
     private final User user;
     public SportEquipmentPost(String object_name,Sport sport, int price, String description, String url , String picture_local, User user){
         this.object_name=object_name;
         this.sport=sport;
         this.price=price;
         this.description=description;
         this.url=url;
         this.picture_local=picture_local;
         this.user=user;

     }

    public int getPrice() {
        return price;
    }
 public String getObject_name(){
    return this.object_name;
}

    public Sport getSport() {
        return sport;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public User getUser() {
        return user;
    }

    public String getPicture_local() {
        return picture_local;
    }
}





