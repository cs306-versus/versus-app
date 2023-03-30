package com.github.versus;

import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.github.versus.user.CustomPlace;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * An activity that displays a map showing the place at the device's current location.
 */


public class LocationFragment extends Fragment implements OnMapReadyCallback {

    private static final String TAG = LocationFragment.class.getSimpleName();
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    // Keys for storing activity state.
    // [START maps_current_place_state_keys]
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";

    private static LatLng localPos;

    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.
    private final LatLng defaultLocation = new LatLng(-33.8523341, 151.2106085);
    public static GoogleMap map;//TODO
    private CameraPosition cameraPosition;
    // The entry point to the Places API.
    private PlacesClient placesClient;
    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient fusedLocationProviderClient;
    private boolean locationPermissionGranted;
    public static CircleOptions circleOptions;//TODO See if its a good idea
    private EditText editTextRadius;
    private Location lastKnownLocation;
    private static LatLng epfl;
    private float radius;
    public static MarkerOptions epflMarker;

    private static final int M_MAX_ENTRIES = 5;
    private static String[] likelyPlaceNames;
    private String[] likelyPlaceAddresses;
    private List[] likelyPlaceAttributions;
    private LatLng[] likelyPlaceLatLngs;
    private AlertDialog placesDialog;
    private  ListView listView ;
    private boolean hasLocations = false;
    private Circle mapCircle;


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
            String API_KEY = getContext().getPackageManager().getApplicationInfo(getContext().getPackageName(), PackageManager.GET_META_DATA).metaData.getString("com.google.android.geo.API_KEY");
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

        return view;
    }

    /**
     * Manipulates the map when it's available.
     * This callback is triggered when the map is ready to be used.
     */
    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;
        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setCompassEnabled(true);
        this.map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
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
        epfl = new LatLng(46.520536, 6.568318);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(epfl, 15));

        epflMarker = new MarkerOptions().position(epfl).title("EPFL");

        map.addMarker(epflMarker);


        map.moveCamera(CameraUpdateFactory.newLatLng(epfl));
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
     * Handles a click on the menu option to get a place.
     *
     * @param item The menu item to handle.
     * @return Boolean.
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.option_get_place) {
            openPlacesDialog();

        }




        return true;
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

    private void getLocationPermission() {
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



    private void openPlacesDialog2() {


      /* AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

       builder.setTitle("Select a place");

       // Add an EditText to get the radius value
       LinearLayout layout = new LinearLayout(requireActivity());
       layout.setOrientation(LinearLayout.VERTICAL);
       View view = LayoutInflater.from(getActivity()).inflate(R.layout.radius_layout, null);
        EditText radiusInput = view.findViewById(R.id.edit_text_radius2);

       /*radiusInput.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
       radiusInput.setHint("Enter radius (in meters)");
       layout.addView(radiusInput);*/
       /*AlertDialog dialog = new AlertDialog.Builder(getActivity()).setTitle("Enter radius").setView(view).setPositiveButton("Show Places", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               // Get the radius entered by the user
               float radius = Float.parseFloat(radiusInput.getText().toString());

               // Update the CircleOptions object with the new radius and redraw the circle
               if(circleOptions!=null){
                 drawCircle(radius);
               }

           }
       }).setNegativeButton("Cancel", null).create();

       final ListView listView = new ListView(requireActivity());
       listView.setAdapter(new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1, likelyPlaceNames));
       listView.setId(R.id.test_list_view);
       layout.addView(listView);
       builder.setView(layout);





       // Set the listener for the list view
       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // The user has selected a place. Add a marker and move the camera to the selected place.
               LatLng selectedPlace = likelyPlaceLatLngs[position];
               Marker marker=map.addMarker(new MarkerOptions()
                       .title(likelyPlaceNames[position])
                       .position(selectedPlace)
                       .snippet(likelyPlaceAddresses[position]));
               addBlinkingMarker(selectedPlace, likelyPlaceNames[position], likelyPlaceAddresses[position]);
               map.moveCamera(CameraUpdateFactory.newLatLngZoom(selectedPlace, DEFAULT_ZOOM));
               placesDialog.dismiss();
           }
       });

       // Set the positive button to filter locations by radius
       /*builder.setPositiveButton("Show Places", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               String radiusStr = radiusInput.getText().toString();
               if (!TextUtils.isEmpty(radiusStr)) {
                   double radius = Double.parseDouble(radiusStr);
                   showCurrentPlace(radius);

               } else {
                   showToast("Please enter a radius");
               }
           }
       });
       // Set the negative button
       builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               dialog.dismiss();
           }
       });


       placesDialog = builder.create();
       placesDialog.show()*/
    }


    /*private void openPlacesDialog() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.radius_layout, null);

        // Get a reference to the EditText view in the layout
        EditText editText = view.findViewById(R.id.edit_text_radius2);

        // Create a dialog to display the EditText view
        AlertDialog dialog = new AlertDialog.Builder(getActivity()).setTitle("Enter radius").setView(view).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Get the radius entered by the user
               radius = Float.parseFloat(editText.getText().toString());
                //listView(radius);

                // Update the CircleOptions object with the new radius and redraw the circle
                if (circleOptions != null) {
                    drawCircle(radius);

                }
                if (radius!=0) {
                    // radius = Double.parseDouble(radiusStr);
                    showCurrentPlace(radius);

                } else {
                    showToast("Please enter a radius");
                }

                    listView(radius);
                }
        }).setNegativeButton("Cancel", null).create();
        dialog.show();

    }




        private void listView(float radius) {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
            builder.setTitle("Select a place");

            LinearLayout layout = new LinearLayout(requireActivity());
            layout.setOrientation(LinearLayout.VERTICAL);
            final ListView listView = new ListView(requireActivity());
            listView.setAdapter(new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1, likelyPlaceNames));
            listView.setId(R.id.test_list_view);
            layout.addView(listView);
            builder.setView(layout);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    LatLng selectedPlace = likelyPlaceLatLngs[position];
                    Marker marker = map.addMarker(new MarkerOptions().title(likelyPlaceNames[position]).position(selectedPlace).snippet(likelyPlaceAddresses[position]));
                    addBlinkingMarker(selectedPlace, likelyPlaceNames[position], likelyPlaceAddresses[position]);
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(selectedPlace, DEFAULT_ZOOM));
                    placesDialog.dismiss();
                }
            });

            builder.setPositiveButton("Show Places", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (radius != 0) {
                        showCurrentPlace(radius);
                    } else {
                        showToast("Please enter a radius");
                    }
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            placesDialog = builder.create();
            placesDialog.show();
        }


*/

private void openPlacesDialog(){
    View view = LayoutInflater.from(getActivity()).inflate(R.layout.radius_layout, null);

    // Get a reference to the EditText view in the layout
    EditText radiusInput = view.findViewById(R.id.edit_text_radius2);
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
                showCurrentPlace(radius);
                dialog.dismiss();
            }
            else  {
                showToast("Please enter a radius");
            }


        }
    }).setNegativeButton("Cancel", null).create();

    dialog.show();


}







   private void showCurrentPlace(double radius) {
        if (map == null) {
            return;
        }
        List<CustomPlace> customPlaces = Arrays.asList(new CustomPlace("UNIL Football", "UNIL Football", new LatLng(46.519385, 6.580856)),
                new CustomPlace("Chavannes Football", "Chavannes Football", new LatLng(46.52527373363714, 6.57366257779824)),
                new CustomPlace("Bassenges Football","Bassenges Football",new LatLng(46.52309381914529, 6.5608807098372175))

        );

        if (locationPermissionGranted) {

            FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
            @SuppressWarnings("MissingPermission") Task<Location> lastLocation = fusedLocationClient.getLastLocation();


            lastLocation.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        LatLng userLatLng = new LatLng(location.getLatitude(), location.getLongitude());

                        List<CustomPlace> filteredPlaces = new ArrayList<>();
                        for (CustomPlace customPlace : customPlaces) {
                            double distance = haversineDistance(userLatLng, customPlace.latLng);

                            if (distance <= radius) {
                                filteredPlaces.add(customPlace);
                                hasLocations=true;
                            }

                        }



                            int count = filteredPlaces.size();

                        likelyPlaceNames = new String[count];
                        likelyPlaceAddresses = new String[count];
                        likelyPlaceAttributions = new List[count];
                        likelyPlaceLatLngs = new LatLng[count];

                        for (int i = 0; i < count; i++) {
                            CustomPlace customPlace = filteredPlaces.get(i);
                            likelyPlaceNames[i] = customPlace.name;
                            likelyPlaceAddresses[i] = customPlace.address;
                            likelyPlaceLatLngs[i] = customPlace.latLng;
                        }

                        // Show a dialog offering the user the list of custom places, and add a
                        // marker at the selected place.
                        if(!hasLocations && radius != 0){
                            showToast("No locations found within the selected radius");

                        }
                        else {
                            showPlacesList();
                        }
                        hasLocations=false;

                       // drawCircle(radius);
                    }
                }
            });
        } else {
            // The user has not granted permission.
            Log.i(TAG, "The user did not grant location permission.");

            getLocationPermission();
        }
    }
    /*private void showPlacesList() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle("Select a place");

        listView = new ListView(requireActivity());
        listView.setAdapter(new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1, likelyPlaceNames));
        listView.setId(R.id.test_list_view);
        builder.setView(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*LatLng selectedPlace = likelyPlaceLatLngs[position];
                Marker marker = map.addMarker(new MarkerOptions()
                        .title(likelyPlaceNames[position])
                        .position(selectedPlace)
                        .snippet(likelyPlaceAddresses[position]));
                addBlinkingMarker(selectedPlace, likelyPlaceNames[position], likelyPlaceAddresses[position]);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(selectedPlace, DEFAULT_ZOOM));
                placesDialog.dismiss();


            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        placesDialog = builder.create();


        placesDialog.show();
    }*/

    private void showPlacesList() {
        //AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        View customView = LayoutInflater.from(getActivity()).inflate(R.layout.custom_alert_dialog, null);
       // listView = customView.findViewById(R.id.test_list_view2);
       // listView.setAdapter(new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1, likelyPlaceNames));
        AlertDialog dialog = new AlertDialog.Builder(getActivity()).setTitle("Select a place").setView(customView).
                setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create();

        //dialog.setView(customView);

        dialog.show();
        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            /*LatLng selectedPlace = likelyPlaceLatLngs[position];
            Marker marker = map.addMarker(new MarkerOptions()
                    .title(likelyPlaceNames[position])
                    .position(selectedPlace)
                    .snippet(likelyPlaceAddresses[position]));
            addBlinkingMarker(selectedPlace, likelyPlaceNames[position], likelyPlaceAddresses[position]);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(selectedPlace, DEFAULT_ZOOM));
                //dialog.dismiss();
            }
        });

*/

    }



    /*private void showPlacesList() {

            // Create a ListView
            ListView listView = new ListView(getActivity());
            listView.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, likelyPlaceNames));
            listView.setId(R.id.test_list_view);

            // Set the onItemClick listener for the ListView
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    LatLng selectedPlace = likelyPlaceLatLngs[position];
                    Marker marker=map.addMarker(new MarkerOptions()
                            .title(likelyPlaceNames[position])
                            .position(selectedPlace)
                            .snippet(likelyPlaceAddresses[position]));
                    //addBlinkingMarker(selectedPlace, likelyPlaceNames[position], likelyPlaceAddresses[position]);
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(selectedPlace, DEFAULT_ZOOM));
                    //dialog.dismiss();

                }
            });

            // Create an AlertDialog with the ListView
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Select a place")
                    .setView(listView)
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            // Show the AlertDialog
            AlertDialog dialog = builder.create();
            dialog.show();
        }
*/



  /*  private void showPlacesList() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.radius_layout, null);
        AlertDialog dialog = new AlertDialog.Builder(getActivity()).setTitle("Enter radius").setView(view).setPositiveButton("Show Places", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Get the radius entered by the user
                String radiusStr = radiusInput.getText().toString();
                if (!TextUtils.isEmpty(radiusStr)) {
                    radius = Float.parseFloat(radiusInput.getText().toString());

                    showCurrentPlace(radius);
                }
                else  {
                    showToast("Please enter a radius");
                }


            }
        }).setNegativeButton("Cancel", null).create();

        dialog.show();
    }
    */



    //Utilitary methods
     private void showToast(String message) {
         LayoutInflater inflater = getLayoutInflater();
         View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) requireActivity().findViewById(R.id.custom_toast_root));

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



    private void drawCircle( double radius) {
        //Clearing the map from previous circles
        map.clear();
        CircleOptions circleOptions = new CircleOptions().center(localPos).radius(radius).
                strokeWidth(2);
        mapCircle = map.addCircle(circleOptions);
        applyFlashingAnimation(mapCircle);
    }


    public static double haversineDistance(LatLng latLng1, LatLng latLng2) {
        double earthRadius = 6371; // Radius of the earth in km
        double dLat = Math.toRadians(latLng2.latitude - latLng1.latitude);
        double dLng = Math.toRadians(latLng2.longitude - latLng1.longitude);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(latLng1.latitude)) * Math.cos(Math.toRadians(latLng2.latitude))
                * Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadius * c * 1000; // Distance in meters
    }
    private void applyBlinkingAnimation(Marker marker) {
        final Handler handler = new Handler();
        final Runnable blinkingRunnable = new Runnable() {
            @Override
            public void run() {
                if (marker.isVisible()) {
                    marker.setVisible(false);
                } else {
                    marker.setVisible(true);
                }
                handler.postDelayed(this, 500); // Change the duration of the blinking effect here (in milliseconds)
            }
        };
        handler.postDelayed(blinkingRunnable, 500);
    }


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
    private void addBlinkingMarker(LatLng position, String title, String snippet) {
        Marker mainMarker = map.addMarker(new MarkerOptions()
                .position(position)
                .title(title)
                .snippet(snippet));

        MarkerOptions blinkingMarkerOptions = new MarkerOptions()
                .position(position)
                .title(title)
                .snippet(snippet)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .alpha(0.5f)
                .visible(false);
        Marker blinkingMarker = map.addMarker(blinkingMarkerOptions);

        applyBlinkingAnimation(blinkingMarker);
    }






}
