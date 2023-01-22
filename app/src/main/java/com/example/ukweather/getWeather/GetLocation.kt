package com.example.ukweather.getWeather

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.core.app.ActivityCompat
import com.example.ukweather.MainActivity
import com.example.ukweather.constanse.ConstanseDb
import com.example.ukweather.db.DbManager
import com.example.ukweather.getResult
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import org.json.JSONArray
import org.json.JSONObject

class getLocation(contextM: Context, nowClimate: MutableState<climate>, todayClimate: MutableState<ArrayList<climate>>, daysOfWeekClimate: MutableState<ArrayList<climate>>) {
    private lateinit var fLocationClient : FusedLocationProviderClient
    var contextGL = contextM
    var climateState = nowClimate
    val todayClimate = todayClimate
    val daysOfWeekClimate = daysOfWeekClimate
    val dbManager = DbManager(contextM)
    fun getLoc(){
        Log.d("ml", "find location")
        fLocationClient = LocationServices.getFusedLocationProviderClient(contextGL)
        if(!isLocationEnabled()){
            Toast.makeText(contextGL, "Увімкніть GPS", Toast.LENGTH_SHORT).show()
            return
        }else {
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
            Log.d("ml", "inspection of gps is checked")
            val task = fLocationClient.lastLocation
            task.addOnCompleteListener {
                    //Log.d("ml", "да вопще афігенно ")
                    Log.d("ml", "location found")
                        getResult(
                            "lat=${it.result.latitude}&lon=${it.result.longitude}",
                            contextGL,
                            climateState,
                            dbManager,
                            ConstanseDb.TIME_COLUMN_VALUE_CURRENT_FOR,
                            todayClimate,
                            daysOfWeekClimate
                        )
                    Log.d("ml", "weather current is found")
                        getResult(
                            "lat=${it.result.latitude}&lon=${it.result.longitude}",
                            contextGL,
                            climateState,
                            dbManager,
                            ConstanseDb.TIME_COLUMN_VALUE_TODAY_FOR,
                            todayClimate,
                            daysOfWeekClimate
                        )

                }
        }
    }
    fun isLocationEnabled(): Boolean{
        val lm = contextGL.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }
}