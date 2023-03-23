package com.github.versus.announcements;

import static android.app.AlertDialog.*;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import com.github.versus.R;
import com.github.versus.db.FsPostManager;
import com.github.versus.posts.Location;
import com.github.versus.posts.Post;
import com.github.versus.posts.Timestamp;
import com.github.versus.sports.Sport;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.Month;
import java.util.ArrayList;

public class CreatePostDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        Builder builder = new Builder(getActivity());
        FsPostManager fpm = new FsPostManager(FirebaseFirestore.getInstance());
        builder.setMessage("create a post")
                .setPositiveButton("post", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Post post = new Post(
                                "Created From App!!!",
                                new Timestamp(2023, Month.APRIL, 3,1, 30, Timestamp.Meridiem.AM),
                                new Location("EPFL", 42, 40),
                                new ArrayList<>(),
                                5,
                                Sport.SOCCER
                        );
                        fpm.insert(post);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}