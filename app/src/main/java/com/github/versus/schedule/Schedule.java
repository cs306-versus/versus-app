package com.github.versus.schedule;

import com.github.versus.posts.Post;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Schedule{
    private final String UID;
    private Set<Post> scheduledPosts = new HashSet<Post>();

    /**
     * creates a Schedule object
     * @param UID unique id of the user who has this schedule
     */
    public Schedule(String UID){
        this.UID = UID;
    }

    /**
     *
     * @return the UID corresponding to this scheduile
     */
    public String getUID() {
        return UID;
    }

    /**
     *
     * @return all the scheduled Posts in chronological order
     * */
    public List<Post> getScheduledPosts() {
        return scheduledPosts;
    }

    /**
     * adds a post to the schedule Object
     * @param p the post to add
     */
    public void addPost(Post p){

    }
    /**
     * adds a post to the schedule Object
     * @param p the post to remove
     */
    public void removePost(Post p){

    }
}
