package com.github.versus.announcements;




import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;


import java.util.Arrays;
import java.util.List;


/**
 * A DialogFragment class for choosing a location.
 * It uses the Google Places API for selecting a location.
 */
public class LocationPickerDialog extends DialogFragment {

    /**
     * Interface for location selection events.
     */
    public interface LocationListener extends CancelCreate {
        /**
         * Called when a location is selected.
         *
         * @param place The selected location.
         */
        public void onLocationPositiveClick(Place place);
    }

    // Listener for location selection events.
    LocationListener locationListener;

    // Request code for the autocomplete activity. Can be any integer.
    public static int AUTOCOMPLETE_REQUEST_CODE = 12345;

    // Google Places API key
    private String API_KEY;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Get the parent fragment, which should implement LocationListener.
        locationListener = (LocationListener) getParentFragment();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Initialize Places API
        try {
            API_KEY = getContext().getPackageManager().getApplicationInfo(getContext().getPackageName(), PackageManager.GET_META_DATA).metaData.getString("com.google.android.geo.API_KEY");
            Places.initialize(getActivity().getApplicationContext(), API_KEY);
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }

        builder.setTitle("Choose a location")
                .setPositiveButton("Choose", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Start the autocomplete activity when the "Choose" button is clicked.
                        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
                        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                                .build(getActivity());
                        getParentFragment().startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Call the onCancel method of the location listener when the "Cancel" button is clicked.
                        locationListener.onCancel();
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
    }
}

