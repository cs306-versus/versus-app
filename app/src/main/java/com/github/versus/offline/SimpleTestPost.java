package com.github.versus.offline;

import com.github.versus.posts.Location;
import com.github.versus.posts.Post;
import com.github.versus.posts.Timestamp;
import com.github.versus.sports.Sport;
import com.github.versus.user.DummyUser;
import com.github.versus.user.User;
import com.github.versus.user.VersusUser;

import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public final class SimpleTestPost extends Post {

    public SimpleTestPost(){
      this("Valid Post");
    }
    public SimpleTestPost(String title){
        super(title,new Timestamp(Calendar.getInstance().get(Calendar.YEAR),
                Month.values()[0], 1, 8, 1, Timestamp.Meridiem.PM)
                ,new Location("Lausanne", 10, 10),new ArrayList<>(),10,Sport.FOOTBALL);

    }
    public static Post postWith(String title,Timestamp timestamp,Location location, int limit){
       return  new Post(title,timestamp,location,new ArrayList<>(),limit, Sport.FOOTBALL);
    }

    public static Post postWith(String title,Timestamp timestamp,Location location, int limit,Sport sport){
        return  new Post(title,timestamp,location,new ArrayList<>(),limit, sport);
    }

    public static Post postWith(String title,Timestamp timestamp,Location location, int limit,Sport sport,VersusUser creator){
        List<VersusUser> ls = new ArrayList<>();
        ls.add(creator);
        return new Post(title,timestamp,location,ls,limit, sport);
    }

}
