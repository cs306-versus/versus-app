package com.github.versus.announcements;

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

        changeColorButton(dialog,Color.WHITE);

        return dialog;
    }

    /**
     * Callback for when the dialog is starting.
     */
    @Override
    public  void onStart() {
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

    /**
     * Changes the dimensions of a provided Window object according to specified percentages of the screen dimensions.
     * The method also applies a background drawable resource to the window.
     *
     * @param window The Window object whose dimensions are to be changed.
     * @param displayMetrics The DisplayMetrics object that contains information about the display, such as its size, density, and font scaling.
     * @param percentageScreenWidth The percentage of the screen's width that the window should take up.
     * @param percentageScreenHeight The percentage of the screen's height that the window should take up.
     */
    public static void changeWindowDimensions(Window window, DisplayMetrics displayMetrics, double percentageScreenWidth, double percentageScreenHeight){
        // Apply rounded corners
        window.setBackgroundDrawableResource(R.drawable.custom_dialog_background);
        // Post a runnable to resize the dialog
        window.getDecorView().post(() -> {
            // Define how much width and height you want to set
            int dialogWindowWidth = (int) (displayMetrics.widthPixels * percentageScreenWidth);
            int dialogWindowHeight = (int) (displayMetrics.heightPixels * percentageScreenHeight);

            // Set size
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(window.getAttributes());
            layoutParams.width = dialogWindowWidth;
            layoutParams.height = dialogWindowHeight;
            window.setAttributes(layoutParams);
        });
    }

    /**
     * Changes the color of the positive and negative buttons of the provided AlertDialog.
     * The color is applied when the dialog is shown, not before.
     *
     * @param dialog The AlertDialog object whose button colors are to be changed.
     * @param color The color to be applied to the buttons. This should be a resolved color, not a resource id.
     */

    public static void changeColorButton(AlertDialog dialog, int color) {
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(color);
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(color);
            }
        });
    }


    /**
     * Callback for when the dialog is attached to a context.
     */
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
    }
}
