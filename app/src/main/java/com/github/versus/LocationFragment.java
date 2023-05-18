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
import android.graphics.drawable.GradientDrawable;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
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
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
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
    // Keys for storing activity state.
    // [START maps_current_place_state_keys]
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";
    private static final int M_MAX_ENTRIES = 5;
    public static GoogleMap map;//TODO
    public static MarkerOptions epflMarker;
    private static LatLng localPos;
    private static boolean locationPermissionGranted;
    private static Location lastKnownLocation;
    private static LatLng epfl;
    private static LatLng google;
    private static String[] likelyPlaceNames;
    private static String[] likelyPlaceAddresses;
    private static LatLng[] likelyPlaceLatLngs;
    private static ListView listView;
    private static boolean hasLocations = false;
    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.
    private final LatLng defaultLocation = new LatLng(-33.8523341, 151.2106085);
    private CameraPosition cameraPosition;
    // The entry point to the Places API.
    private PlacesClient placesClient;
    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient fusedLocationProviderClient;
    private EditText editTextRadius;
    private float radius;
    //private Marker lastClickedMarker;
    private Circle lastDrawnCircle;
    private DataBaseManager dummyLocationManager;

    private List<CustomPlace> customPlaces;
    public static  String API_KEY;
    private Button drawPathButton;
    private LatLng   bc = new LatLng(46.51906462963576, 6.561923350291548);
    private LatLng selectedPlace ;
    private int posSelectedPlace;
    // Threshold distance to consider a field as nearby (in meters)
    private double thresholdDistanceInput=1000;
    private  AutocompleteSupportFragment autocompleteFragment;
    private Marker blinkingMarker ;
    private Marker visibleMarker;
    private Polyline lastDrawnLine;


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
        Button drawPathButton = view.findViewById(R.id.draw_path_button);
        drawPathButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace originLatLng and destinationLatLng with the actual LatLng objects
                if(selectedPlace != null){
                    FetchDirectionsTask fetchDirectionsTask = new FetchDirectionsTask(localPos, selectedPlace);
                    fetchDirectionsTask.execute();
                }
                else {
                    showToast("No selected place !");
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
                    if (blinkingMarker != null) {
                        blinkingMarker.remove();
                    }
                    if (visibleMarker != null) {
                        visibleMarker.remove();
                    }

                    // Add a new marker at the selected place
                    visibleMarker = map.addMarker(new MarkerOptions().title(place.getName()).position(selectedPlace).snippet(place.getAddress()));
                    blinkingMarker = map.addMarker(new MarkerOptions().position(selectedPlace).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).title("Clicked Location"));

                    // Add a blinking marker at the selected place
                    addBlinkingMarker(selectedPlace, place.getName(), place.getName());
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
        MenuItem radiusItem = menu.findItem(R.id.option_get_place);

        // Get the EditText view from the menu item
        editTextRadius = (EditText) radiusItem.getActionView();

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
        if (item.getItemId() == R.id.option_get_place) {
            openPlacesDialog();

        } else if (item.getItemId() == R.id.option_choose_location) {
            if (lastDrawnCircle != null) {
                lastDrawnCircle.remove();
            }
            if (visibleMarker != null) {
                visibleMarker.remove();
            }
            enableCustomLocationSelection();
        }
        else if (item.getItemId() == R.id.option_choose_radius) {
            if (lastDrawnCircle != null) {
                lastDrawnCircle.remove();
            }
            if (visibleMarker != null) {
                visibleMarker.remove();
            }
            chooseDefaultRadius();
        }
        else if (item.getItemId() == R.id.search_bar) {
            if (lastDrawnCircle != null) {
                lastDrawnCircle.remove();
            }
            if (visibleMarker != null) {
                visibleMarker.remove();
            }
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


        // Remove the last clicked marker if it exists
        if (blinkingMarker != null) {
            blinkingMarker.remove();
        }

        // Add a new marker to the map at the clicked position
        blinkingMarker = map.addMarker(new MarkerOptions().position(clickedPosition).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).title("Clicked Location"));

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

        view = LayoutInflater.from(getActivity()).inflate(R.layout.custom_radius_layout, null);

        radiusInput = view.findViewById(R.id.edit_text_radius3);


        // Get a reference to the EditText view in the layout

        radiusInput.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        radiusInput.setHint("Enter radius (in meters)");
        AlertDialog dialog = new AlertDialog.Builder(getActivity()).setTitle("Enter radius").setView(view).setPositiveButton("Enter", new DialogInterface.OnClickListener() {
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
        dialog.show();
    }

    /**
     * Opens a custom dialog for users to input a radius value (in meters).
     * After the user enters the radius and clicks "Show Places," the app will show the current place
     * and draw a circle with the given radius.
     */
    public void openPlacesDialog() {
        View view;
        EditText radiusInput;

        view = LayoutInflater.from(getActivity()).inflate(R.layout.radius_layout, null);
        radiusInput = view.findViewById(R.id.edit_text_radius2);


        // Get a reference to the EditText view in the layout

        radiusInput.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        radiusInput.setHint("Enter radius (in meters)");

        // Create a dialog to display the EditText view
        AlertDialog dialog = new AlertDialog.Builder(getActivity()).setTitle("Enter radius").setView(view).setPositiveButton("Show Places", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Get the radius entered by the user
                String radiusStr = radiusInput.getText().toString();
                if (!TextUtils.isEmpty(radiusStr)) {
                    radius = Float.parseFloat(radiusInput.getText().toString());
                    dialog.dismiss();
                    showCurrentPlaces(radius);
                    drawCircle(radius);
                } else {
                    dialog.dismiss();
                    showToast("Please enter a radius");
                }


            }
        }).setNegativeButton("Cancel", null).create();

        dialog.show();

    }

    /**
     * Shows a list of custom places within a specified radius around the user's current location.
     * If no custom places are found within the radius, a toast message is displayed to the user.
     *
     * @param radius The radius (in meters) around the user's current location to search for custom places.
     */
    public void showCurrentPlaces(double radius) {
        //moving to our current location if we changed it
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(localPos, 15));
        List<CustomPlace> filteredPlaces = new ArrayList<>();
        for (CustomPlace customPlace : customPlaces) {
            double distance = haversineDistance(localPos, customPlace.latLng);

            if (distance <= radius) {
                filteredPlaces.add(customPlace);
                hasLocations = true;
            }

        }
        int count = filteredPlaces.size();

        likelyPlaceNames = new String[count];
        likelyPlaceAddresses = new String[count];
        likelyPlaceLatLngs = new LatLng[count];

        for (int i = 0; i < count; i++) {
            CustomPlace customPlace = filteredPlaces.get(i);
            likelyPlaceNames[i] = customPlace.name;
            likelyPlaceAddresses[i] = customPlace.address;
            likelyPlaceLatLngs[i] = customPlace.latLng;
        }

        // Show a dialog offering the user the list of custom places, and add a
        // marker at the selected place.
        if (!hasLocations && radius != 0) {
            showToast("No locations found within the selected radius");

        } else {
            showPlacesList();
            drawCircle(radius);
        }
        hasLocations = false;

    }


    /**
     * Creates and displays a custom dialog containing a list of nearby custom places.
     * When a user selects a place from the list, a blinking marker is added to the selected place on the map,
     * and the camera moves to focus on the selected place with the default zoom level.
     */

    private void showPlacesList() {
        View customView = LayoutInflater.from(getActivity()).inflate(R.layout.custom_alert_dialog, null);
        listView = customView.findViewById(R.id.test_list_view2);
        listView.setAdapter(new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1, likelyPlaceNames));
        AlertDialog dialog = new AlertDialog.Builder(getActivity()).setTitle("Select a place").setView(customView).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create();

        dialog.setView(customView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPlace= likelyPlaceLatLngs[position];
                posSelectedPlace =position;
                if (visibleMarker != null) {
                    visibleMarker.remove();
                }
                visibleMarker = map.addMarker(new MarkerOptions().title(likelyPlaceNames[position]).position(selectedPlace).snippet(likelyPlaceAddresses[position]));

                addBlinkingMarker(selectedPlace, likelyPlaceNames[position], likelyPlaceAddresses[position]);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(selectedPlace, DEFAULT_ZOOM));
                dialog.dismiss();
            }
        });
        selectedPlace= likelyPlaceLatLngs[posSelectedPlace];
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
     * Draws a circle with the specified radius around a predefined center point (localPos) on the map.
     * The method clears the map of any previous circles and applies a flashing animation to the newly drawn circle.
     *
     * @param radius The radius (in meters) of the circle to be drawn.
     */
    private void drawCircle(double radius) {
        if (lastDrawnCircle != null) {
            //Clearing the map from previous circles
            lastDrawnCircle.remove();
        }


        CircleOptions circleOptions = new CircleOptions().center(localPos).radius(radius).strokeWidth(2);
        lastDrawnCircle = map.addCircle(circleOptions);
        applyFlashingAnimation(lastDrawnCircle);
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
     * Applies a flashing animation to a given circle on the map by altering its stroke and fill colors' alpha values.
     *
     * @param circle The circle to apply the flashing animation to.
     */

    private void applyFlashingAnimation(Circle circle) {
        ValueAnimator animator = ValueAnimator.ofInt(100, 255);
        animator.setDuration(1000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int alpha = (int) animation.getAnimatedValue();
                circle.setStrokeColor(Color.argb(alpha, 26, 115, 232));
                circle.setFillColor(Color.argb(alpha / 2, 26, 115, 232));
            }
        });
        animator.start();
    }

    /**
     * Adds a blinking marker to the map at the specified position with the provided title and snippet.
     *
     * @param position The LatLng position of the marker to be added.
     * @param title    The title of the marker.
     * @param snippet  The snippet of the marker.
     */
    private void addBlinkingMarker(LatLng position, String title, String snippet) {

        MarkerOptions blinkingMarkerOptions = new MarkerOptions().position(position).title(title).snippet(snippet).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).alpha(0.5f).visible(false);
        if (blinkingMarker != null) {
            //Clearing the map from previous circles
            blinkingMarker.remove();
        }
        blinkingMarker = map.addMarker(blinkingMarkerOptions);

        applyBlinkingAnimation(blinkingMarker);
    }
    private void drawPath(GoogleMap map, List<LatLng> points) {
        if (lastDrawnCircle != null) {
            //Clearing the map from previous circles
            lastDrawnCircle.remove();
        }

        if (lastDrawnLine != null) {
            //Clearing the map from previous circles
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
        private double distanceValue;
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
            } catch (MalformedURLException e) {
                errorMessage = "Error in the URL";
                Log.e("FetchDirectionsTask", errorMessage, e);
            } catch (IOException e) {
                errorMessage = "Error connecting to the API";
                Log.e("FetchDirectionsTask", errorMessage, e);
                e.printStackTrace();
            } catch (JSONException e) {
                errorMessage = "Error parsing the JSON response";
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