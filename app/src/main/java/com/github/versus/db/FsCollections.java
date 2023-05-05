package com.github.versus.db;

public enum FsCollections {
    CHATS("chats"),LOCATIONS("locations"),SCHEDULES("schedules"), POSTS("posts"), TEST_POSTS("test_posts"), USERS("users");

    private final String name;

    private FsCollections(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return name ;
    }
}
