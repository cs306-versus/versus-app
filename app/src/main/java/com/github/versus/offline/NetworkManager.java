package com.github.versus.offline;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;

/**
 * A class that manages the internet connectivity of the application
 */
public final class NetworkManager {
    private NetworkManager(){}

    /**
     * Returns if the app has access to network connection
     * @param context: the context of the application
     * @return
     * true iff the application has access to network connection.
     */
    public static boolean isNetworkAvailable(Context context) {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.
                                                    getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkCapabilities capabilities = connectivityManager.
                                                getNetworkCapabilities(connectivityManager.getActiveNetwork());
            if (capabilities != null) {
                connected= capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET);
            }
        }
        return connected;
    }

}
