package com.example.ukweather
import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.core.content.ContextCompat
import com.example.ukweather.constanse.ConstanseDb
import com.example.ukweather.constanse.ConstanseWeather
import com.example.ukweather.db.DbManager
import com.example.ukweather.db.timeNow
import com.example.ukweather.getWeather.climate
import com.example.ukweather.getWeather.getLocation
import org.json.JSONObject
import java.util.*

class MainActivity : ComponentActivity() {
    private lateinit var  pLauncher: ActivityResultLauncher<String>
    lateinit var myDbManager : DbManager
    @RequiresApi(Build.VERSION_CODES.M)
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerPermissionListener()
        chekPermission()

    }

    override fun onDestroy() {
        super.onDestroy()
        myDbManager.closeDb()
    }
    @RequiresApi(Build.VERSION_CODES.M)
    @ExperimentalMaterialApi
    private fun chekPermission(){
        when{
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED ->{
                myDbManager = DbManager(this)
                myDbManager.openDb()


                setContent {
                    val climateState = remember { mutableStateOf(climate()) }
                    val todayClimate = remember { mutableStateOf(ArrayList<JSONObject>()) }
                    val getLoc = getLocation(this, climateState, todayClimate)
                    getLoc.init()
                    getLoc.getLoc(ConstanseWeather.CURRENT_WEATHER)
                    getLoc.getLoc(ConstanseWeather.FORECAST_OF_DAY)

                    Navigation(this, climateState, todayClimate)
                }
                    }
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) ->{
                buildPermissionAlertDialog()
            }
            else -> {
                pLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    @ExperimentalMaterialApi
    private fun buildPermissionAlertDialog(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Ви повинні дозволити використовувати GPS")
        builder.setMessage("Щоб користуватися цим додатком вам потрібно дозволити GPS. Ми його використовуємо лише щоб" +
                " дізнатись про погоду в вашій місцевості та показати її вам.")
        builder.setPositiveButton("Дозволити"){dialog, i ->
            pLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        builder.show()
    }

    @ExperimentalMaterialApi
    private fun registerPermissionListener(){
        pLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            if(it){
                myDbManager = DbManager(this)
                myDbManager.openDb()

                setContent {
                    val climateState = remember { mutableStateOf(climate()) }
                    val todayClimate = remember { mutableStateOf(ArrayList<JSONObject>()) }
                    val getLoc = getLocation(this, climateState, todayClimate)
                    getLoc.init()
                    getLoc.getLoc(ConstanseWeather.CURRENT_WEATHER)
                    getLoc.getLoc(ConstanseWeather.FORECAST_OF_DAY)

                    Navigation(this, climateState, todayClimate)
                }
            }else{
                buildPermissionAlertDialog()
            }
        }
    }
}






