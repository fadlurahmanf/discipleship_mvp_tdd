package com.fadlurahmanf.starter_app_mvp.data.interceptor

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConnectivityInterceptor @Inject constructor(
    private var context: Context
):Interceptor {


    private fun isConnectedInternet():Boolean{
        val cm:ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                var capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
                return (capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true
                        || capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true
                        || capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) == true)
            }
        }
        return false
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isConnectedInternet()){
            throw IOException("No network available")
        }else{
            return chain.proceed(chain.request())
        }
    }
}