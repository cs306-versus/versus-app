package com.github.versus.announcements;

import static android.app.AlertDialog.Builder;
import static android.app.AlertDialog.OnClickListener;
import static com.github.versus.announcements.CreatePostTitleDialogFragment.changeWindowDimensions;

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
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.github.versus.R;
import com.github.versus.db.FsPostManager;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Dialog Fragment for specifying the maximum player count.
 */
public class MaxPlayerDialogFragment extends DialogFragment {

    /**
     * Interface for listening to user actions within the dialog.
     */
    public interface MaxPlayerListener extends CancelCreate {
        /**
         * Callback for when the user submits a maximum player count.
         *
         * @param playerCount The selected maximum player count.
         */
        void onMaxPlayerPositiveClick(int playerCount);
    }

    private int maxPlayerCount = 2;  // Default max player count
    private MaxPlayerListener tl;  // Listener for user actions

    /**
     * Callback for when the dialog is first created.
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Fragment fragment = getParentFragment();
        tl = (MaxPlayerListener) fragment;
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        Builder builder = new Builder(getActivity());
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
     * Callback for when the fragment is first attached to its context.
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    /**
     * Callback for when the fragment starts.
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
                changeWindowDimensions(window,displayMetrics,0.85,0.25);
            }
        }
    }
}
