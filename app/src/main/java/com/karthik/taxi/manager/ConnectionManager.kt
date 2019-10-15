package com.karthik.taxi.manager

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo


/**
 * created by Karthik A on 18/12/18
 */
class ConnectionManager {

    /**
     * method to check whether the device has active internet connection
     */
    fun isOnline(context: Context): Boolean {

        val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connMgr.activeNetworkInfo
        return networkInfo?.isConnected == true

    }
}