package com.github.versus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import com.github.versus.db.FsPostManager;
import com.github.versus.posts.Location;
import com.github.versus.posts.Post;
import com.github.versus.posts.Timestamp;
import com.github.versus.sports.Sport;
import com.github.versus.user.DummyUser;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.*;


import com.github.versus.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.nio.file.FileStore;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * basic class used to model the behavior of creating a group
 * and joining a group
 * to be adapted to be a part of the announcement view
 * (used for backend behavior setup)
 */
public class groupCreJoin extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_cre_join);

    }
    public void createPost(View view) {
        Post p = new Post( "haha", new Timestamp(2023, Month.AUGUST, 18, 12, 15, Timestamp.Meridiem.AM) ,
                new Location("tirane",15, 16), new ArrayList<>(), 15, Sport.FOOTBALL);
        FsPostManager postm = new FsPostManager(db);
        postm.insert(p);
        }
    public void JoinGroup(View view) throws ExecutionException, InterruptedException {
        FsPostManager postm = new FsPostManager(db);
        System.out.println(postm.fetch("haha").get().getLocation());
    }
}