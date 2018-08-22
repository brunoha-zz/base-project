package br.com.brunoalmeida.baseproject.common.util

import android.content.Context
import android.net.ConnectivityManager
import br.com.brunoalmeida.baseproject.App

open class Utils {
    companion object {
        fun hasNetWork(): Boolean {
            val context = App.mInstance
            val connectivityManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            var activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (connectivityManager.activeNetworkInfo != null) {
                activeNetworkInfo = connectivityManager.activeNetworkInfo
            }
            return (activeNetworkInfo != null && activeNetworkInfo.isConnected)
        }

    }
}