package com.github.versus;

import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.toRadians;

import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.versus.db.DataBaseManager;
import com.github.versus.db.DummyLocationManager;
import com.github.versus.user.CustomPlace;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;




public class LocationFragment extends Fragment implements OnMapReadyCallback {

    private static final String TAG = LocationFragment.class.getSimpleName();
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";

    public static GoogleMap map;//TODO

    private static LatLng localPos;
    private static boolean locationPermissionGranted;
    private static Location lastKnownLocation;

    private static LatLng google;
    private static String[] likelyPlaceNames;
    private static String[] likelyPlaceAddresses;
    private static LatLng[] likelyPlaceLatLngs;

    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.
    private final LatLng defaultLocation = new LatLng(-33.8523341, 151.2106085);
    private CameraPosition cameraPosition;
    // The entry point to the Places API.
    private PlacesClient placesClient;
    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient fusedLocationProviderClient;
    private DataBaseManager dummyLocationManager;

    private List<CustomPlace> customPlaces;
    public static  String API_KEY;

    private LatLng   bc = new LatLng(46.51906462963576, 6.561923350291548);
    private LatLng selectedPlace ;

    // Threshold distance to consider a field as nearby (in meters)
    private double thresholdDistanceInput=1000;
    private  AutocompleteSupportFragment autocompleteFragment;
    private Marker blueMarker ;
    private Marker redMarker;
    private Polyline lastDrawnLine;
    private double distanceValue;
    private Marker blinkingMarker;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);
        //call setHasOptionsMenu(true) to notify the fragment
        // that it has options menu items that need to be created


        setHasOptionsMenu(true);

        // Retrieve location and camera position from saved instance state.
        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }

        // Construct a PlacesClient and retrieve the API Key from local.properties file
        try {
            API_KEY = getContext().getPackageManager().getApplicationInfo(getContext().getPackageName(), PackageManager.GET_META_DATA).metaData.getString("com.google.android.geo.API_KEY");
            Places.initialize(getActivity().getApplicationContext(), API_KEY);


        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
        placesClient = Places.createClient(getActivity());

        // Construct a FusedLocationProviderClient.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        // Build the map
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Button nearMeButton = view.findViewById(R.id.near_me_button);
        nearMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace originLatLng and destinationLatLng with the actual LatLng objects

                    if(redMarker!= null){
                        redMarker.remove();
                    }
                    if(blinkingMarker!= null){
                        blinkingMarker.remove();
                    }
                    if (lastDrawnLine != null) {

                        lastDrawnLine.remove();
                    }
                    if(blueMarker != null){
                        findClosestFields(blueMarker.getPosition());
                    }
                    else {
                        findClosestFields(localPos);
                    }
                }



        });


        // Get the AutocompleteSupportFragment from the FragmentManager using its ID
        autocompleteFragment = (AutocompleteSupportFragment)
                getChildFragmentManager().findFragmentById(R.id.autocomplete_location_search);

        // Set the visibility of the autocompleteFragment's view to GONE, meaning it will not be visible, nor take up any space
        autocompleteFragment.getView().setVisibility(View.GONE);

        // Set the fields that should be included for the place (location) selected in the search bar
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));

        // Set a listener that gets triggered when a place is selected from the search suggestions
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // Log the details of the selected place
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());

                // Store the LatLng of the selected place
                selectedPlace = place.getLatLng();

                // If the map is not null and a place has been selected, move the camera to the selected place and hide the autocompleteFragment's view
                if (map != null && selectedPlace != null) {
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(selectedPlace, DEFAULT_ZOOM));
                    autocompleteFragment.getView().setVisibility(View.GONE);

                    // Remove the previous markers if they exist

                    // Add a new marker at the selected place
                     blueMarker= map.addMarker(new MarkerOptions().title(place.getName()).position(selectedPlace).snippet(place.getAddress()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

                }
            }

            @Override
            public void onError(Status status) {
                // Log if there is any error while selecting a place
                Log.i(TAG, "An error occurred: " + status);
            }
        });



        return view;
    }

    /**
     * Manipulates the map when it's available.
     * This callback is triggered when the map is ready to be used.
     */
    @Override
    public void onMapReady(GoogleMap map) {
        LocationFragment.map = map;
        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setCompassEnabled(true);
        LocationFragment.map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            // Return null here, so that getInfoContents() is called next.
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                // Inflate the layouts for the info window, title and snippet.
                View infoWindow = getLayoutInflater().inflate(R.layout.custom_info_contents, getActivity().findViewById(R.id.map), false);

                TextView title = infoWindow.findViewById(R.id.title);
                title.setText(marker.getTitle());

                TextView snippet = infoWindow.findViewById(R.id.snippet);
                snippet.setText(marker.getSnippet());

                return infoWindow;
            }
        });


        // Add markers for EPFL and Satellite

        google = new LatLng(37.42, -122.084);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(google, 15));

        dummyLocationManager = new DummyLocationManager();
        try {
            customPlaces = (List<CustomPlace>) dummyLocationManager.fetch("Places").get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {

            throw new RuntimeException(e);
        }
        //  map.moveCamera(CameraUpdateFactory.newLatLng(epfl));
        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                LatLng position = marker.getPosition();
                String message = "Marker clicked at: " + position.latitude + ", " + position.longitude;
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        });


        // Prompt the user for permission.
        getLocationPermission();

        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();

        // Get the current location of the device and set the position of the map.
        getDeviceLocation();
    }

    /**
     * Saves the state of the map when the activity is paused.
     *
     * @param outState The Bundle object to save the state.
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (map != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, map.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, lastKnownLocation);
        }
        super.onSaveInstanceState(outState);
    }

    /**
     * Sets up the options menu.
     *
     * @param menu The options menu.
     * @return Boolean.
     */

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.current_place_menu, menu);

        // Get the menu item that contains the EditText view

        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * Handles a click on the menu option to get a place or choose a random location.
     *
     * @param item The menu item to handle.
     * @return Boolean.
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.option_choose_location) {

            if(redMarker!= null){
                redMarker.remove();
            }
            if(blueMarker != null){
                blueMarker.remove();
            }
            if(blinkingMarker!= null){
                blinkingMarker.remove();
            }
            if (lastDrawnLine != null) {
                lastDrawnLine.remove();
            }


          enableCustomLocationSelection();
        }
        else if (item.getItemId() == R.id.option_choose_radius) {

            if(redMarker!= null){
                redMarker.remove();
            }

            if(blinkingMarker!= null){
                blinkingMarker.remove();
            }
            if (lastDrawnLine != null) {
                lastDrawnLine.remove();
            }
          map.setOnMapClickListener(null);

          chooseDefaultRadius();
        }
        else if (item.getItemId() == R.id.search_bar) {

            if(blueMarker != null){
                blueMarker.remove();
            }
            if(redMarker!= null){
                redMarker.remove();
            }
            if(blinkingMarker!= null){
                blinkingMarker.remove();
            }
            if (lastDrawnLine != null) {
                lastDrawnLine.remove();
            }
          map.setOnMapClickListener(null);

          autocompleteFragment.getView().setVisibility(View.VISIBLE);
        }

        return true;
    }
    /**
     * Enables custom location selection by setting a map click listener.
     * When the map is clicked, it calls the findClosestFields method to find the closest fields to the clicked position.
     */
    private void enableCustomLocationSelection() {

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                if(redMarker!= null){
                    redMarker.remove();
                }

                if(blinkingMarker!= null){
                    blinkingMarker.remove();
                }
                if (lastDrawnLine != null) {

                    lastDrawnLine.remove();
                }
                if (blueMarker!= null) {

                    blueMarker.remove();
                }

                // Find closest fields to the clicked position
                findClosestFields(latLng);
            }
        });
    }



    /**
     * Finds the closest fields to a clicked position on the map.
     * Iterates through the custom places and finds the ones within the threshold distance.
     * Prepares the data for the showPlacesList() method and shows the list of nearby fields.
     *
     * @param clickedPosition LatLng object representing the position where the user clicked on the map.
     */
    private void findClosestFields(LatLng clickedPosition) {

        // Add a new marker to the map at the clicked position
        blueMarker=map.addMarker(new MarkerOptions().position(clickedPosition).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).title("Clicked Location"));

        // Iterate through the custom places and find the ones within the threshold distance
        List<CustomPlace> nearbyFields = customPlaces.stream().filter(place -> haversineDistance(clickedPosition, place.latLng) <= thresholdDistanceInput).collect(Collectors.toList());


        // Prepare the data for the showPlacesList() method
        if(!nearbyFields.isEmpty()) {
            likelyPlaceNames = new String[nearbyFields.size()];
            likelyPlaceAddresses = new String[nearbyFields.size()];
            likelyPlaceLatLngs = new LatLng[nearbyFields.size()];

            for (int i = 0; i < nearbyFields.size(); i++) {
                likelyPlaceNames[i] = nearbyFields.get(i).name;
                likelyPlaceAddresses[i] = nearbyFields.get(i).address;
                likelyPlaceLatLngs[i] = nearbyFields.get(i).latLng;
            }
            // Show the list of nearby fields
            showPlacesList();
        }
        else{
            showToast("No locations found within the selected radius");
        }
    }

    /**
     * Gets the current location of the device, and positions the map's camera.
     */
    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */

        try {
            if (locationPermissionGranted) {
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(getActivity(), new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            lastKnownLocation = task.getResult();

                            if (lastKnownLocation != null) {
                                localPos = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());

                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(localPos, DEFAULT_ZOOM));
                            }
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
                            map.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }


    /**
     * Prompts the user for permission to use the device location.
     */

    void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }


    /**
     * Handles the result of the request for location permissions.
     *
     * @param requestCode  The request code passed to requestPermissions().
     * @param permissions  The requested permissions.
     * @param grantResults The grant results for the corresponding permissions.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        locationPermissionGranted = false;
        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationPermissionGranted = true;
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        updateLocationUI();
    }





    /**
     * Updates the map's UI settings based on whether the user has granted location permission.
     */
    private void updateLocationUI() {
        if (map == null) {
            return;
        }
        try {
            if (locationPermissionGranted) {
                map.setMyLocationEnabled(true);
                map.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                map.setMyLocationEnabled(false);
                map.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }
    private void chooseDefaultRadius(){

        View view;
        EditText radiusInput;
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        // Inflate custom layout
        view = inflater.inflate(R.layout.custom_radius_layout, null);

        // Find your EditText view
        radiusInput = view.findViewById(R.id.edit_text_radius3);

        // Get a reference to the EditText view in the layout

        radiusInput.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        radiusInput.setHint("Enter radius (in meters)");

        // Create a dialog to display the EditText view
        AlertDialog dialog = new AlertDialog.Builder(getActivity(), R.style.CustomAlertDialog).setView(view).setPositiveButton("Enter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Get the radius entered by the user
                String radiusStr = radiusInput.getText().toString();
                if (!TextUtils.isEmpty(radiusStr)) {
                    thresholdDistanceInput = Float.parseFloat(radiusInput.getText().toString());
                    dialog.dismiss();

                } else {
                    dialog.dismiss();
                }

            }
        }).setNegativeButton("Cancel", null).create();

        // Customize the dialog window appearance here
        Window window = dialog.getWindow();
        if (window != null) {
           changeWindowDimensions(window);
        }


        dialog.show();
    }

    /**
     * Modifies the dimensions of a given window to certain percentages of the screen's width and height.
     * Also sets the window's background drawable resource.
     *
     * @param window The window that will have its dimensions modified and background set.
     */
    private void changeWindowDimensions(Window window) {
        // Set the window's background drawable resource
        window.setBackgroundDrawableResource(R.drawable.custom_dialog_background);

        // Apply the resizing logic inside a post() call on the window's decor view,
        // which ensures that it is executed after the window has been laid out.
        window.getDecorView().post(() -> {
            // Obtain the current display metrics
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

            // Calculate new dimensions as percentages of the screen's width and height
            int dialogWindowWidth = (int) (displayMetrics.widthPixels * 0.85); // 85% of screen width
            int dialogWindowHeight = (int) (displayMetrics.heightPixels * 0.25); // 25% of screen height

            // Create a new WindowManager.LayoutParams object and copy the current window's attributes into it
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(window.getAttributes());

            // Set the new dimensions
            layoutParams.width = dialogWindowWidth;
            layoutParams.height = dialogWindowHeight;

            // Apply the new attributes to the window
            window.setAttributes(layoutParams);
        });
    }

    /**
     * Creates and displays a custom dialog containing a list of nearby custom places.
     * When a user selects a place from the list, a blinking marker is added to the selected place on the map,
     * and the camera moves to focus on the selected place with the default zoom level.
     */

    private void showPlacesList() {
        // Create a custom title
        TextView title = new TextView(getActivity());
        title.setText("Select a place");
        title.setBackgroundColor(getResources().getColor(R.color.main_app_color)); // Set the background color to your preference
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(24);
        title.setTypeface(null, Typeface.BOLD);

        // Create a custom array adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_single_choice, likelyPlaceNames){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                TextView item = (TextView) super.getView(position, convertView, parent);

                // Set the color of the items in the list
                item.setTextColor(Color.WHITE);

                return item;
            }
        };

        // Create a dialog with radio buttons
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomAlertDialog);
        builder.setCustomTitle(title)
                .setSingleChoiceItems(adapter, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // User checked an item, update your selectedPlace
                        selectedPlace = likelyPlaceLatLngs[which];
                        redMarker=map.addMarker(new MarkerOptions().title(likelyPlaceNames[which]).position(selectedPlace).snippet(likelyPlaceAddresses[which]));
                        addBlinkingMarker(selectedPlace, likelyPlaceNames[which], likelyPlaceAddresses[which]);
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(selectedPlace, DEFAULT_ZOOM));
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK, you might want to do something here
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();

        // Customize dialog appearance here...
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(R.drawable.custom_dialog_background);

            window.getDecorView().post(() -> {
                DisplayMetrics displayMetrics = new DisplayMetrics();
                getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int dialogWindowWidth = (int) (displayMetrics.widthPixels * 0.85); // 85% of screen width
                int dialogWindowHeight = (int) (displayMetrics.heightPixels * 0.35); // 25% of screen height

                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.copyFrom(window.getAttributes());
                layoutParams.width = dialogWindowWidth;
                layoutParams.height = dialogWindowHeight;
                window.setAttributes(layoutParams);
            });
        }

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.WHITE);
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.WHITE);
            }
        });

        dialog.show();
    }




    /**
     * Displays a custom toast with the specified message.
     * The custom toast has a specific layout, background color, and corner radius.
     *
     * @param message The message to be displayed in the custom toast.
     */

    private void showToast(String message) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, requireActivity().findViewById(R.id.custom_toast_root));

        TextView text = layout.findViewById(R.id.custom_toast_text);
        text.setText(message);

        // Set background programmatically
        GradientDrawable shape = new GradientDrawable();
        shape.setColor(Color.parseColor("#1D4EB5"));
        shape.setCornerRadius(24);
        layout.setBackground(shape);

        Toast toast = new Toast(requireActivity());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }



    /**
     * Calculates the haversine distance between two LatLng points in meters.
     *
     * @param latLng1 The first LatLng point.
     * @param latLng2 The second LatLng point.
     * @return The haversine distance between the two points in meters.
     */
    private double haversineDistance(LatLng latLng1, LatLng latLng2) {
        double earthRadius = 6371; // Radius of the earth in km
        double dLat = toRadians(latLng2.latitude - latLng1.latitude);
        double dLng = toRadians(latLng2.longitude - latLng1.longitude);
        double a = sin(dLat / 2) * sin(dLat / 2) + cos(toRadians(latLng1.latitude)) * cos(toRadians(latLng2.latitude)) * sin(dLng / 2) * sin(dLng / 2);
        double c = 2 * atan2(sqrt(a), sqrt(1 - a));
        return earthRadius * c * 1000; // Distance in meters
    }
    /**
     * Applies a blinking animation to a given marker on the map.
     *
     * @param marker The marker to apply the blinking animation to.
     */

    private void applyBlinkingAnimation(Marker marker) {
        final Handler handler = new Handler();
        final Runnable blinkingRunnable = new Runnable() {
            @Override
            public void run() {
                marker.setVisible(!marker.isVisible());
                handler.postDelayed(this, 500); // Change the duration of the blinking effect here (in milliseconds)
            }
        };
        handler.postDelayed(blinkingRunnable, 500);
    }



    /**
     * Adds a blinking marker to the map at the specified position with the provided title and snippet.
     *
     * @param position The LatLng position of the marker to be added.
     * @param title    The title of the marker.
     * @param snippet  The snippet of the marker.
     */
    private void addBlinkingMarker(LatLng position, String title, String snippet) {

        FetchDirectionsTask fetchDirectionsTask = new FetchDirectionsTask(blueMarker.getPosition(), selectedPlace);
        fetchDirectionsTask.execute();

        MarkerOptions blinkingMarkerOptions = new MarkerOptions().position(position).title(title).snippet(snippet).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).alpha(0.5f).visible(false);

        blinkingMarker= map.addMarker(blinkingMarkerOptions);

        applyBlinkingAnimation(blinkingMarker);
    }
    private void drawPath(GoogleMap map, List<LatLng> points) {
        if (lastDrawnLine != null) {
            lastDrawnLine.remove();
        }


        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.addAll(points);
        polylineOptions.width(10);
        polylineOptions.color(Color.BLUE);
        lastDrawnLine = map.addPolyline(polylineOptions);




    }
    private class FetchDirectionsTask extends AsyncTask<Void, Void, List<LatLng>> {
        private LatLng origin;
        private LatLng destination;
        private String errorMessage;

        private String distanceText;

        public FetchDirectionsTask(LatLng origin, LatLng destination) {
            this.origin = origin;
            this.destination = destination;
            this.errorMessage = null;
        }

        @Override
        protected List<LatLng> doInBackground(Void... voids) {
            // Fetch the directions from the Google Maps Directions API
            try {
                // Prepare the URL for the API request
                String url = "https://maps.googleapis.com/maps/api/directions/json?origin=" +
                        origin.latitude + "," + origin.longitude +
                        "&destination=" + destination.latitude + "," + destination.longitude +
                        "&key=" + API_KEY;

                URL apiUrl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
                connection.setRequestMethod("GET");

                // Get the response from the API
                try (InputStream inputStream = connection.getInputStream();
                     BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }

                    // Parse the response and extract the path points
                    JSONObject jsonResponse = new JSONObject(stringBuilder.toString());
                    JSONArray routes = jsonResponse.getJSONArray("routes");
                    if (routes.length() > 0) {
                        JSONObject route = routes.getJSONObject(0);
                        JSONArray legs = route.getJSONArray("legs");
                        JSONObject leg = legs.getJSONObject(0);
                        JSONArray steps = leg.getJSONArray("steps");

                        // Get the LatLng points for each step
                        List<LatLng> pathPoints = new ArrayList<>();
                        for (int i = 0; i < steps.length(); i++) {
                            JSONObject step = steps.getJSONObject(i);
                            JSONObject startLocation = step.getJSONObject("start_location");
                            double startLat = startLocation.getDouble("lat");
                            double startLng = startLocation.getDouble("lng");
                            pathPoints.add(new LatLng(startLat, startLng));
                        }

                        // Add the destination point
                        JSONObject endLocation = leg.getJSONObject("end_location");
                        double endLat = endLocation.getDouble("lat");
                        double endLng = endLocation.getDouble("lng");
                        pathPoints.add(new LatLng(endLat, endLng));
                        JSONObject distanceObject = leg.getJSONObject("distance");
                        distanceText = distanceObject.getString("text");
                        distanceValue = distanceObject.getDouble("value");

                        return pathPoints;
                    } else {
                        errorMessage = "No routes found in the API response";
                    }
                }
            }
            catch (Exception e) {
                errorMessage = "An error occurred";
                Log.e("FetchDirectionsTask", errorMessage, e);
            }

            return null;
        }


        @Override
        protected void onPostExecute(List<LatLng> result) {
            if (result != null) {
                drawPath(map, result);
                showToast("Distance: " + distanceText);
            } else {
                if (errorMessage != null) {
                    showToast(errorMessage);
                } else {
                    showToast("Failed to fetch directions");
                }

            }
        }
    }




}