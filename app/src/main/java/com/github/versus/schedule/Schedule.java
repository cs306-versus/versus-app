package com.github.versus.schedule;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.versus.posts.Post;
import com.github.versus.posts.Timestamp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Schedule{
    @JsonProperty("UID")
    private String UID;
    private List<Post> posts = new ArrayList<>();

    /**
     * creates a Schedule object
     * @param UID unique id of the user who has this schedule
     */
    public Schedule(String UID){
        this.UID = UID;
    }
    public Schedule(){
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
    public List<Post> getPosts() {
        return posts.stream().sorted((p1, p2) ->
             p1.getDate().isBefore(p2.getDate())
        ).collect(Collectors.toList());
    }

    /**
     * adds a single post to the schedule Object
     * @param p the post to add
     */
    public void addPost(Post p){
        posts.add(p);
    }

    /**
     * adds multiple post to the schedule Object
     * @param posts the posts to add
     */
    public void addPosts(Set<Post> posts){
        for (Post p: posts
             ) {
            posts.add(p);
        }
    }

    /**
     * adds a post to the schedule Object
     * @param p the post to remove
     */
    public void removePost(Post p){
        posts.remove(p);
    }


    /**
     * Returns a new Schedule object with all scheduled posts that occur on or after
     * the specified date.
     *
     * @param t the timestamp representing the starting date
     * @return a new Schedule object with the filtered set of scheduled posts
     */
    public Schedule startingFromDate(Timestamp t){
        Schedule result =  new Schedule(this.UID);
        result.addPosts(posts.stream().filter(
                post -> t.isBefore(post.getDate()) <=  0
        ).collect(Collectors.toSet()));
        return result;
    }

    /**
     * Returns a new Schedule object with all scheduled posts that occur on
     * the specified date.
     *
     * @param t the timestamp representing the  date
     * @return a new Schedule object with the filtered set of scheduled posts
     */
    public Schedule onDate(Timestamp t){
        Schedule result =  new Schedule(this.UID);
        result.addPosts(this.posts.stream().filter(
                post -> {
                    Timestamp date = post.getDate() ;
                    return date.getYear() == t.getYear() && date.getMonth() == t.getMonth() && date.getDay() == t.getDay();
                }
        ).collect(Collectors.toSet()));
        return result;
    }

    /**
     *
     * @return all the attributes of the post in a map fashion
     */
    public Map<String, Object> getAllAttributes() {
        Map<String, Object> res =  new HashMap<String, Object>();
        res.put("UID", UID );
        res.put("posts", posts);
        return res;

    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Schedule)) {
            return false;
        }

        Schedule other = (Schedule) obj;
        return this.UID.equals(other.UID)
                && this.posts.equals(((Schedule) obj).posts);
    }

}
