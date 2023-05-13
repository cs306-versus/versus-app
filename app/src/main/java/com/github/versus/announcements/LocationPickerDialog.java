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


public class LocationPickerDialog extends DialogFragment {
    LocationListener locationListener;
    // AUTOCOMPLETE_REQUEST_CODE could be any integer you like
    public static int AUTOCOMPLETE_REQUEST_CODE = 12345;
    private String API_KEY ;
    public interface LocationListener extends CancelCreate {
        public void onLocationPositiveClick(Place place);
    }


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


                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
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
