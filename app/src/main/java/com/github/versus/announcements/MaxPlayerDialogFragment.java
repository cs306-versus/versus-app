package com.github.versus.announcements;

import static android.app.AlertDialog.*;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.github.versus.R;
import com.github.versus.db.FsPostManager;
import com.github.versus.sports.Sport;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MaxPlayerDialogFragment extends DialogFragment  {

    public interface MaxPlayerListener extends CancelCreate {
        public void onMaxPlayerPositiveClick(int playerCount);
    }

    int maxPlayerCount = 2;

    MaxPlayerListener tl;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        Activity a = getActivity();
        Fragment f = getParentFragment();
        tl = (MaxPlayerListener) f;
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        Builder builder = new Builder(a);
        FsPostManager fpm = new FsPostManager(FirebaseFirestore.getInstance());
        View innerView = inflater.inflate(R.layout.create_post_max_players, null);
        builder.setTitle("max number of players").setView(innerView)
                .setPositiveButton("Next", new OnClickListener() {
                    public void onClick(DialogInterface dialog, int id){
                       EditText et =  ((EditText) innerView.findViewById(R.id.editMaxPlayers));
                       System.out.println(et.getText());
                        maxPlayerCount = Integer.parseInt(et.getText().toString());
                        tl.onMaxPlayerPositiveClick(maxPlayerCount);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        tl.onCancel();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
    }
}