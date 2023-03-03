package com.github.aderouic.firebase_bootcamp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void get(View view) {
        EditText email = ((EditText) findViewById(R.id.editTextTextEmailAddress));
        Editable num = ((EditText) findViewById(R.id.editTextPhone)).getText();
        CompletableFuture<String> future = new CompletableFuture<>();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child(num.toString()).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    future.completeExceptionally(new NoSuchFieldException());
                } else {
                    future.complete((String) dataSnapshot.getValue());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                future.completeExceptionally(e);
            }
        });

        future.thenAccept(new Consumer<String>() {
            @Override
            public void accept(String s) {
                email.setText(s);
            }
        });


    }
    public void set(View view) {
        Editable email = ((EditText) findViewById(R.id.editTextTextEmailAddress)).getText();
        Editable num = ((EditText) findViewById(R.id.editTextPhone)).getText();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child(num.toString()).setValue(email.toString());
    }
}