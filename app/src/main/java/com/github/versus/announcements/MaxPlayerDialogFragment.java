package com.github.versus.announcements;

import static android.app.AlertDialog.*;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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

public class MaxPlayerDialogFragment extends DialogFragment {

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
        builder.setView(innerView).setPositiveButton("Next", new OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                EditText et = ((EditText) innerView.findViewById(R.id.editMaxPlayers));
                System.out.println(et.getText());
                maxPlayerCount = Integer.parseInt(et.getText().toString());
                tl.onMaxPlayerPositiveClick(maxPlayerCount);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                tl.onCancel();
            }
        });
        // Create the AlertDialog object and return it
        AlertDialog dialog = builder.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.WHITE);
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.WHITE);
            }
        });


        return dialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            Window window = dialog.getWindow();
            if (window != null) {
                // Apply rounded corners
                window.setBackgroundDrawableResource(R.drawable.custom_dialog_background);

                // Post a runnable to resize the dialog
                window.getDecorView().post(() -> {
                    // Get current screen size
                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

                    // Define how much width and height you want to set
                    int dialogWindowWidth = (int) (displayMetrics.widthPixels * 0.85); // 85% of screen width
                    int dialogWindowHeight = (int) (displayMetrics.heightPixels * 0.25); // 85% of screen height

                    // Set size
                    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                    layoutParams.copyFrom(window.getAttributes());
                    layoutParams.width = dialogWindowWidth;
                    layoutParams.height = dialogWindowHeight;
                    window.setAttributes(layoutParams);
                });
            }
        }
    }
}