package com.github.versus.offline;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;

public final class NetworkManager {

    private NetworkManager(){}
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
