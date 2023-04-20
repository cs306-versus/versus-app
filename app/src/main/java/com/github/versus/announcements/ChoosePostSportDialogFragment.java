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

public class ChoosePostSportDialogFragment extends DialogFragment {

    public interface SportListener extends CancelCreate {
        public void onSportPositiveClick(Sport sport);
    }

    SportListener tl;
    Sport sport = Sport.SOCCER;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        Activity a = getActivity();
        Fragment f = getParentFragment();
        tl = (SportListener) f;
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        Builder builder = new Builder(a);
        FsPostManager fpm = new FsPostManager(FirebaseFirestore.getInstance());
        final String[] items = Stream.of(Sport.values()).map(sport -> sport.name).collect(Collectors.toList()).toArray(new String[0]);
        builder.setTitle("choose a sport").setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sport = Sport.values()[i];
                    }
                })
                .setPositiveButton("next", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        tl.onSportPositiveClick(sport);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
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