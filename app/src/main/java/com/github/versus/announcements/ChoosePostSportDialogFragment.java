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
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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
        Activity a = getActivity();
        Fragment f = getParentFragment();
        tl = (SportListener) f;
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        // Inflate custom layout
        View dialogView = inflater.inflate(R.layout.custom_dialog_layout_list, null);

        // Get RadioGroup from the custom layout and set the items
        RadioGroup radioGroup = dialogView.findViewById(R.id.radioGroup);
        final String[] items = Stream.of(Sport.values()).map(sport -> sport.name).collect(Collectors.toList()).toArray(new String[0]);

        // Add RadioButton for each sport
        // Add RadioButton for each sport
        for (int i = 0; i < items.length; i++) {
            RadioButton radioButton = new RadioButton(a);
            radioButton.setText(items[i]);
            radioButton.setId(i);
            radioButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            radioButton.setTextColor(Color.WHITE);

            // Set text size
            int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, a.getResources().getDisplayMetrics()); // Convert 10dp to pixels
            radioButton.setPadding(padding, padding, padding, padding); // Set padding
            radioGroup.addView(radioButton);
        }
        // When a RadioButton is clicked
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> sport = Sport.values()[checkedId]);

        // Use the Builder class for convenient dialog construction
        Builder builder = new Builder(a);
        builder.setView(dialogView);




        builder.setPositiveButton("Next", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        tl.onSportPositiveClick(sport);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        tl.onCancel();
                    }
                });

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
                    int dialogWindowHeight = (int) (displayMetrics.heightPixels * 0.47); // 30% of screen height

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

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
    }
}