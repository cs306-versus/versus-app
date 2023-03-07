package com.github.versus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import com.github.versus.database.Post;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.;


import com.github.versus.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.CompletableFuture;

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
        Intent intent = getIntent();
    }
    public void createPost(View view) {
        Post post = new
        db.collection("Posts")
                .add()
    }
    public void JoinGroup(View view) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child(num.toString()).setValue(email.toString());
    }
}