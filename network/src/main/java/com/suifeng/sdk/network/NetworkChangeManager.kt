package com.suifeng.sdk.network

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.Keep
import androidx.annotation.RestrictTo

object NetworkChangeManager {

    private val networkListenerList = mutableListOf<NetworkStateChangeCallback>()

    @SuppressLint("MissingPermission")
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    fun initNetworkGlobalListener(context: Context) {
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onLost(network: Network) {
                super.onLost(network)
                notification(NetworkStateData(isConnected = false))
            }

            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                notification(NetworkStateData(isConnected = true))
            }
        }
        (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).apply {

            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.N ->
                    registerDefaultNetworkCallback(networkCallback)

                else ->
                    registerNetworkCallback(NetworkRequest.Builder().build(), networkCallback)
            }
        }
    }

    fun registerNetworkChangeListener(callback: NetworkStateChangeCallback) {
        if (!networkListenerList.contains(callback)) {
            networkListenerList.add(callback)
        }
    }

    fun unRegisterNetworkChangeListener(callback: NetworkStateChangeCallback) {
        networkListenerList.remove(callback)
    }

    private fun notification(networkStateData: NetworkStateData) {
        networkListenerList.forEach {
            it.invoke(networkStateData)
        }
    }
}

typealias NetworkStateChangeCallback = (networkStateData: NetworkStateData) -> Unit

@Keep
data class NetworkStateData(val isConnected: Boolean)