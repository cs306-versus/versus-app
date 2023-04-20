package com.github.versus.db;

import com.github.versus.user.CustomPlace;
import com.google.android.gms.maps.model.LatLng;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * DummyLocationManager is a dummy implementation of the DataBaseManager interface .
 */
public class DummyLocationManager implements DataBaseManager {

    /**
     * This method is not implemented in this dummy class.
     *
     * @param data The data to insert.
     * @return A future containing a boolean indicating the success of the operation.
     */
    @Override
    public Future<Boolean> insert(Object data) {
        return null;
    }

    /**
     * Fetches the custom places from the "database" (hard-coded list of custom places).
     *
     * @param id The identifier of the data to fetch.
     * @return A future containing a list of custom places.
     */
    @Override
    public Future<List<CustomPlace>> fetch(String id) {
        List<CustomPlace> customPlaces = Arrays.asList(
                new CustomPlace("UNIL Football", "UNIL Football", new LatLng(46.519385, 6.580856)),
                new CustomPlace("Chavannes Football", "Chavannes Football", new LatLng(46.52527373363714, 6.57366257779824)),
                new CustomPlace("Bassenges Football", "Bassenges Football", new LatLng(46.52309381914529, 6.5608807098372175)),
                new CustomPlace("GooglePlex Football", "Google Football", new LatLng(37.422083, -122.082555))
        );

        return CompletableFuture.completedFuture(customPlaces);
    }

    /**
     * This method is not implemented in this dummy class.
     *
     * @param id The identifier of the data to delete.
     * @return A future containing a boolean indicating the success of the operation.
     */
    @Override
    public Future<Boolean> delete(String id) {
        return null;
    }
}
