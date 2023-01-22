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
import java.lang.Math.abs
import java.util.*

class MainActivity : ComponentActivity() {
    private lateinit var  pLauncher: ActivityResultLauncher<String>
    lateinit var myDbManager : DbManager
    @RequiresApi(Build.VERSION_CODES.M)
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("ml", "test")

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
                StartCodeOnCreate()
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
                StartCodeOnCreate()
            }else{
                buildPermissionAlertDialog()
            }
        }
    }
    @ExperimentalMaterialApi
    fun StartCodeOnCreate(){

        myDbManager = DbManager(this)
        myDbManager.openDb()

        val calendar: Calendar = GregorianCalendar.getInstance()
        val dayNow = calendar.get(Calendar.DAY_OF_MONTH)
        val monthNow = calendar.get(Calendar.MONTH)

        var monthName = ""
        when(monthNow){
            0 -> monthName = this.getString(R.string.January)
            1 -> monthName = this.getString(R.string.February)
            2 -> monthName = this.getString(R.string.March)
            3 -> monthName = this.getString(R.string.April)
            4 -> monthName = this.getString(R.string.May)
            5 -> monthName = this.getString(R.string.June)
            6 -> monthName = this.getString(R.string.July)
            7 -> monthName = this.getString(R.string.August)
            8 ->  monthName = this.getString(R.string.September)
            9 -> monthName = this.getString(R.string.October)
            10 -> monthName = this.getString(R.string.November)
            11 -> monthName = this.getString(R.string.December)
        }

        setContent {
            val climateState = remember { mutableStateOf(climate()) }
            val todayClimate = remember { mutableStateOf(ArrayList<climate>()) }
            val getLoc = getLocation(this, climateState, todayClimate)
            getLoc.getLoc()

            Navigation(this, climateState, todayClimate, dayNow, monthName)
        }
    }
}






