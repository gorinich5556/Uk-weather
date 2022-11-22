package com.example.ukweather
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.example.ukweather.constanse.ConstanseDb
import com.example.ukweather.constanse.ConstanseWeather
import com.example.ukweather.db.DbManager
import com.example.ukweather.db.timeNow
import com.example.ukweather.getWeather.climate
import com.example.ukweather.getWeather.getLocation
import java.util.*

class MainActivity : ComponentActivity() {
    lateinit var myDbManager : DbManager
    lateinit var temp: MutableState<Int>
    lateinit var climateState: MutableState<climate>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        myDbManager = DbManager(this)
        myDbManager.openDb()

        val date = Date() // given date
        val calendar: Calendar = GregorianCalendar.getInstance()
        val hoursNow = calendar.get(Calendar.HOUR_OF_DAY)
        val dayNow = calendar.get(Calendar.DAY_OF_WEEK)
        Log.d("ml", "current time is: ${hoursNow}")


        //val currentLastTime = myDbManager.readDbData(ConstanseDb.TIME_COLUMN_VALUE_CURRENT_FOR)
        //.d("ml", "hghfhg: ${currentLastTime.time}")


        var a = 0
        //Log.d("ml", "current time is: $curTime")
        //Log.d("ml", "current time is: ${curTime.time}")
        //Log.d("ml", "current time is: ${curTime.get}")
        var clm = climate()
        setContent {
            climateState = remember { mutableStateOf(clm) }
            var temp = remember { mutableStateOf(0) }
            val getLoc = getLocation(this, climateState, myDbManager)
            getLoc.init()
            getLoc.getLoc(ConstanseWeather.CURRENT_WEATHER)
            getLoc.getLoc(ConstanseWeather.FORECAST_OF_DAY)


            Navigation(this, temp, climateState, myDbManager)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        myDbManager.closeDb()
    }
}





