package com.example.ukweather.getWeather

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.core.app.ActivityCompat
import com.example.ukweather.db.DbManager
import com.example.ukweather.getResult
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource

class getLocation(contextM: Context, nowClimate: MutableState<climate>, DbM: DbManager) {
    private lateinit var fLocationClient : FusedLocationProviderClient
    var contextGL = contextM
    var climateState = nowClimate
    val dbManager = DbManager(contextM)
    fun init(){
        fLocationClient = LocationServices.getFusedLocationProviderClient(contextGL)
    }
    fun getLoc(forWeather: String){
        if(!isLocationEnabled()){
            Toast.makeText(contextGL, "Увімкніть GPS", Toast.LENGTH_SHORT).show()
            //Log.d("ml", "справи бажають кращого :(")
            return
        }else {
            //Log.d("ml", "це працює!! :) :) :)")
            val ct = CancellationTokenSource()
            if (ActivityCompat.checkSelfPermission(
                    contextGL,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    contextGL,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            fLocationClient
                .getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, ct.token)
                .addOnCompleteListener {
                    //Log.d("ml", "да вопще афігенно ")
                        getResult(
                            "lat=${it.result.latitude}&lon=${it.result.longitude}",
                            contextGL,
                            climateState,
                            dbManager,
                            forWeather
                        )

                }
        }
    }
    fun isLocationEnabled(): Boolean{
        val lm = contextGL.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }
}