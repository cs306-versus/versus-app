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
import com.google.firebase.firestore.FirebaseFirestore;

public class CreatePostTitleDialogFragment extends DialogFragment {

    public interface TitleListener extends CancelCreate {
        public void onTitlePositiveClick(String title);
    }

    TitleListener tl;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        Activity a = getActivity();
        Fragment f = getParentFragment();
        tl = (TitleListener) f;
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View innerView = inflater.inflate(R.layout.create_post_layout, null);
        Builder builder = new Builder(a);
        FsPostManager fpm = new FsPostManager(FirebaseFirestore.getInstance());
        builder.setMessage("create a post").setView(innerView)
                .setPositiveButton("next", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditText et = innerView.findViewById(R.id.editPostTitle);
                        System.out.println(et);
                        String title = et.getText().toString();
                        tl.onTitlePositiveClick(title);
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
//        tl = (TitleListener) context;
    }

}