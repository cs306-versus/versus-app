package com.github.versus.announcements;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.github.versus.LocationFragment;
import com.github.versus.R;
import com.github.versus.db.FsPostManager;
import com.github.versus.sports.Sport;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LocationPickerDialog extends DialogFragment {



    public interface LocationListener extends CancelCreate {
        public void onLocationPositiveClick(Place place);
    }

        LocationListener locationListener;
        Place place;

        // AUTOCOMPLETE_REQUEST_CODE could be any integer you like
        public static int AUTOCOMPLETE_REQUEST_CODE = 12345;
        private String API_KEY ;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        locationListener = (LocationListener) getParentFragment();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Initialize Places API
        try {
            API_KEY = getContext().getPackageManager().getApplicationInfo(getContext().getPackageName(), PackageManager.GET_META_DATA).metaData.getString("com.google.android.geo.API_KEY");
            Places.initialize(getActivity().getApplicationContext(), API_KEY);


        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (!Places.isInitialized()) {
            Places.initialize(getActivity().getApplicationContext(), API_KEY);
        }
        builder.setTitle("Choose a location")
                .setPositiveButton("Choose", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
                        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                                .build(getActivity());
                       getParentFragment().startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
                        System.out.println("HEHEHEHEHE BOY5");

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        locationListener.onCancel();
                    }
                });
        return builder.create();
    }

        /*@Override
        public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
                if (resultCode == RESULT_OK) {
                    place = Autocomplete.getPlaceFromIntent(data);
                    locationListener.onLocationPositiveClick(place);
                    System.out.println("HEHEHEHEHE BOY1");
                } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                    Status status = Autocomplete.getStatusFromIntent(data);
                    Log.i(TAG, status.getStatusMessage());
                    System.out.println("HEHEHEHEHE BOY2");
                } else if (resultCode == RESULT_CANCELED) {
                    // The user canceled the operation.
                    System.out.println("HEHEHEHEHE BOY3");
                }
                System.out.println("HEHEHEHEHE BOY6");
            }
            System.out.println("HEHEHEHEHE BOY4");
        }*/



    @Override
    public void onAttach(Context context){
        super.onAttach(context);
    }


}
