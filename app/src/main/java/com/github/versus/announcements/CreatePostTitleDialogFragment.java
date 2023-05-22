package com.github.versus.announcements;

import static android.app.AlertDialog.*;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A custom dialog fragment used for entering the title when creating a new post.
 * This dialog allows users to input a post title, and to cancel the post creation process.
 */
public class CreatePostTitleDialogFragment extends DialogFragment {

    /**
     * Listener interface to handle user actions within this dialog.
     */
    public interface TitleListener extends CancelCreate {
        /**
         * Callback for when a title is inputted.
         * @param title The inputted title.
         */
        void onTitlePositiveClick(String title);
    }

    private TitleListener tl;  // Listener for user actions

    /**
     * Callback for when the dialog is first created.
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Fragment fragment = getParentFragment();
        tl = (TitleListener) fragment;
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        // Inflate custom layout
        View innerView = inflater.inflate(R.layout.custom_dialog_post_layout, null);

        // Find your EditText view
        EditText et = innerView.findViewById(R.id.editPostTitle);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomAlertDialog);
        FsPostManager fpm = new FsPostManager(FirebaseFirestore.getInstance());
        builder.setView(innerView)
                .setPositiveButton("Next", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String title = et.getText().toString();
                        tl.onTitlePositiveClick(title);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
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
                // Apply rounded corners
                window.setBackgroundDrawableResource(R.drawable.custom_dialog_background);

                // Post a runnable to resize the dialog
                window.getDecorView().post(() -> {
                    // Get current screen size
                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

                    // Define how much width and height you want to set
                    int dialogWindowWidth = (int) (displayMetrics.widthPixels * 0.85); // 85% of screen width
                    int dialogWindowHeight = (int) (displayMetrics.heightPixels * 0.25); // 25% of screen height

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

    /**
     * Callback for when the dialog is attached to a context.
     */
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
    }
}
