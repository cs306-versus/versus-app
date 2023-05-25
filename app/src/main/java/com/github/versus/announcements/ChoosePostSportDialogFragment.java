package com.github.versus.announcements;

import static android.app.AlertDialog.*;

import static com.github.versus.announcements.CreatePostTitleDialogFragment.changeWindowDimensions;

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

/**
 * A custom dialog fragment used for selecting a sport type when creating a new post.
 * This dialog allows users to select a sport from a list of available sports, and to cancel the post creation process.
 */
public class ChoosePostSportDialogFragment extends DialogFragment {

    /**
     * Listener interface to handle user actions within this dialog.
     */
    public interface SportListener extends CancelCreate {
        /**
         * Callback for when a sport is selected.
         * @param sport The selected sport.
         */
        public void onSportPositiveClick(Sport sport);
    }

    private SportListener tl;  // Listener for user actions
    private Sport sport = Sport.SOCCER;  // The selected sport. Defaults to SOCCER.

    /**
     * Callback for when the dialog is first created.
     */
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

    /**
     * Callback for when the dialog is starting.
     */
    @Override
    public void onStart() {

        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            Window window = dialog.getWindow();
            if (window != null) {
                DisplayMetrics displayMetrics = new DisplayMetrics();
                getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                changeWindowDimensions(window,displayMetrics,0.85,0.47);
            }
        }
    }

    /**
     * Callback for when the dialog is attached to a context.
     */
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
    }
}
