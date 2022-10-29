package com.omarahmed.shoppinglist.core.util.connectivity

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.omarahmed.shoppinglist.core.domain.states.ConnectionState


val Context.currentConnectivityState: ConnectionState
    get() {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return getCurrentConnectivityState(connectivityManager)
    }

fun getCurrentConnectivityState(connectivityManager: ConnectivityManager): ConnectionState {
     if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
        val activeNetwork = connectivityManager.activeNetwork ?: return ConnectionState.Unavailable
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return ConnectionState.Unavailable
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> ConnectionState.Available
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> ConnectionState.Available
            else -> ConnectionState.Unavailable
        }
    } else {
        connectivityManager.run {
            connectivityManager.activeNetworkInfo?.run {
                return when(type) {
                    ConnectivityManager.TYPE_WIFI -> ConnectionState.Available
                    ConnectivityManager.TYPE_MOBILE -> ConnectionState.Available
                    else -> ConnectionState.Unavailable
                }
            }
        }
    }
    return ConnectionState.Unavailable
}