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

        List<CustomPlace> customPlaces = Arrays.asList(new CustomPlace("UNIL Football", "UNIL Football", new LatLng(46.519385, 6.580856)), new CustomPlace("Chavannes Football", "Chavannes Football", new LatLng(46.52527373363714, 6.57366257779824)), new CustomPlace("Bassenges Football", "Bassenges Football", new LatLng(46.52309381914529, 6.5608807098372175)), new CustomPlace("GooglePlex Football", "Google Football", new LatLng(37.422083, -122.082555)), new CustomPlace("CENTRE SPORTIF ECUBLENS", "Chem. du Croset 8", new LatLng(46.5357, 6.5643)), new CustomPlace("LUC American Football", "Av. Favre Louis 5, 1024 Ecublens", new LatLng(46.5236, 6.5689)), new CustomPlace("Terrain de Basketball de Vidy", " Rte de Vidy, 1007 Lausanne", new LatLng(46.518823, 6.593237)), new CustomPlace("Centre sportif de Chavannes-Près-Renens", "Rte de Praz Véguey 30, 1022 Chavannes-près-Renens", new LatLng(46.52489049926697, 6.573665960584042)), new CustomPlace("Street Workout Park Lausanne", "Rte de Chavannes 141, 1007 Lausanne", new LatLng(46.52461415264953, 6.586504156784977)));


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
