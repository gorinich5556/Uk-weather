package com.example.ukweather

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.ukweather.constanse.ConstanseDb
import com.example.ukweather.constanse.ConstanseWeather
import com.example.ukweather.db.DbManager
import com.example.ukweather.db.timeNow
import com.example.ukweather.getWeather.climate
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

fun getResult(locate:String, context: Context, nowClimate: MutableState<climate>, myDbManager: DbManager, whil: String, todayClimate: MutableState<ArrayList<JSONObject>>){
    var url = "empty"
    val queue = Volley.newRequestQueue(context)

    myDbManager.openDb()
    var timeLastSave = myDbManager.readDbData(ConstanseDb.TIME_COLUMN_VALUE_CURRENT_FOR)

    val calendar: Calendar = GregorianCalendar.getInstance()
    val hoursNow = calendar.get(Calendar.HOUR_OF_DAY)
    val dayNow = calendar.get(Calendar.DAY_OF_WEEK)
    val weekNow = calendar.get(Calendar.WEEK_OF_YEAR)
    val minutesNow = calendar.get(Calendar.MINUTE)

    when(whil){
        ConstanseWeather.CURRENT_WEATHER ->{
            url = "https://api.openweathermap.org/data/2.5/weather?$locate&units=metric&appid=${ConstanseWeather.API_KEY}&lang=ua"

        }
        ConstanseWeather.FORECAST_OF_DAY ->{
            url = "https://api.openweathermap.org/data/2.5/forecast?$locate&units=metric&cnt=40&appid=${ConstanseWeather.API_KEY}&lang=ua"

        }
        ConstanseWeather.FORECAST_OF_WEEK ->{
            url = "https://api.openweathermap.org/data/2.5/forecast?$locate&units=metric&cnt=40&appid=${ConstanseWeather.API_KEY}&lang=ua"

        }
    }

    val stringRequest = StringRequest(
        Request.Method.GET,
        url,
        {
                response ->
            //Log.d("ml", "response: $response")

            val obj = JSONObject(response)

            when(whil){
                ConstanseWeather.CURRENT_WEATHER ->{

                    val tempF = (obj.getJSONObject("main")).getString("temp")
                    val tempFell = (obj.getJSONObject("main")).getString("feels_like")
                    val wind = (obj.getJSONObject("wind")).getString("speed")
                    val pressure = (obj.getJSONObject("main")).getString("pressure")
                    val humidity = (obj.getJSONObject("main")).getString("humidity")
                    val weather = (obj.getJSONArray("weather")).getJSONObject(0).getString("description")
                    val nameLoc = (obj.getString("name"))

                    /*
                    Log.d("ml", "location name is: $nameLoc")
                    Log.d("ml", "temp in с: $tempF")
                    Log.d("ml", "response: $response")
                    Log.d("ml", "weather: $weather")*/
                    //val tempC = tempF.toFloat() / 40
                    //Log.d("ml", "temp in c: ${tempC.toString()}")

                    val newClimate = climate()



                    val recTemp = Math.round(tempF.toDouble()).toInt()
                    val recTempFeel = Math.round(tempFell.toDouble()).toInt()
                    val recWind = Math.round(wind.toDouble()).toInt()
                    val recPressure = Math.round(pressure.toDouble()).toInt()
                    val recHumidity = humidity.toInt()
                    val recWeather = weather
                    val recName = nameLoc


                    newClimate.temp = recTemp
                    newClimate.feelslike = recTempFeel
                    newClimate.wind = recWind
                    newClimate.pressure = recPressure
                    newClimate.humidity = recHumidity
                    newClimate.weather = recWeather
                    newClimate.name = recName
                    newClimate.id = 1



                    //Log.d("ml", nee.toString())
                    if(timeLastSave != null) {
                        if (timeLastSave.week == weekNow) {
                            if (timeLastSave.day == dayNow) {
                                if ((hoursNow - timeLastSave.time) >= 2) {
                                    nowClimate.value = newClimate
                                    val timeNowe = timeNow()
                                    timeNowe.fore = ConstanseDb.TIME_COLUMN_VALUE_CURRENT_FOR
                                    timeNowe.week = weekNow
                                    timeNowe.time = hoursNow
                                    timeNowe.day = dayNow
                                    timeNowe.id = 1
                                    myDbManager.updateDataToDb(timeNowe)
                                    myDbManager.currentTempUpdateToDb(newClimate)
                                }
                            } else {
                                nowClimate.value = newClimate
                                val timeNower = timeNow()
                                timeNower.fore = ConstanseDb.TIME_COLUMN_VALUE_CURRENT_FOR
                                timeNower.week = weekNow
                                timeNower.time = hoursNow
                                timeNower.day = dayNow
                                timeNower.id = 1
                                myDbManager.updateDataToDb(timeNower)
                                myDbManager.currentTempUpdateToDb(newClimate)
                            }
                        } else {
                            nowClimate.value = newClimate
                            val timeNowh = timeNow()
                            timeNowh.fore = ConstanseDb.TIME_COLUMN_VALUE_CURRENT_FOR
                            timeNowh.week = weekNow
                            timeNowh.time = hoursNow
                            timeNowh.day = dayNow
                            timeNowh.id = 1
                            myDbManager.updateDataToDb(timeNowh)
                            myDbManager.currentTempUpdateToDb(newClimate)
                        }
                    } else {
                        nowClimate.value = newClimate
                        myDbManager.insertToDb(
                            ConstanseDb.TIME_COLUMN_VALUE_CURRENT_FOR,
                            hoursNow,
                            dayNow,
                            weekNow
                        )
                        myDbManager.currentTempInsertToDb(newClimate)
                    }

                }
                ConstanseWeather.FORECAST_OF_DAY ->{
                    val jsonArray = obj.getJSONArray("list")
                    //Log.d("ml", "item 1: ${jsonArray[0]}")
                    //Log.d("ml", "item 1: ${list[1]}")
                    //Log.d("ml", "item 1: ${list[2]}")
                    //Log.d("ml", "item 1: ${list[39]}")
                    var howMuchShow = 0.0
                    howMuchShow = (24.0 - hoursNow) / 3
                    val result = Math.ceil(howMuchShow).toInt()
                    //Log.d("ml", "how much show after: $result")

                    val list = arrayListOf<JSONObject>()

                    for(i in 0..result){
                        list.add(jsonArray.getJSONObject(i))
                    }
                    //val nextTempMax = list[0].getJSONObject("main").getString("temp_max")
                    //Log.d("ml", "list: $list")
                    //Log.d("ml", "max temp is: $nextTempMax")
                    todayClimate.value = list
                }
                ConstanseWeather.FORECAST_OF_WEEK ->{

                }
            }
        },
        {
                error ->
            Log.d("ml", "error: $error")
        }
    )

    when(whil){
        ConstanseWeather.CURRENT_WEATHER -> {
            if (timeLastSave != null) {
                if (timeLastSave.week == weekNow) {
                    if (timeLastSave.day == dayNow) {
                        if ((hoursNow - timeLastSave.time) >= 2) {
                            queue.add(stringRequest)
                        } else {
                            val oldClimate = myDbManager.currentTempReadDbData()
                            nowClimate.value = oldClimate
                        }
                    } else {
                        queue.add(stringRequest)
                    }
                } else {
                    queue.add(stringRequest)
                }
            }else{
                queue.add(stringRequest)
            }
        }
        ConstanseWeather.FORECAST_OF_DAY ->{
            queue.add(stringRequest)
        }
        ConstanseWeather.FORECAST_OF_WEEK ->{

        }
    }
/*
    if(timeLastSave != null){
        if(timeLastSave.day == dayNow){
            if((timeLastSave.time - hoursNow) >= 2){
                queue.add(stringRequest)
            }else{
                val oldClimate = myDbManager.currentTempReadDbData()
                nowClimate.value = oldClimate
            }
        }else{
            queue.add(stringRequest)
        }
    }else{
        queue.add(stringRequest)
    }
*/
    return
}
/*
fun getRegion(locate:String, context: Context, tempState: MutableState<Int>, nowClimate: MutableState<climate>) {
    val url = "https://api.weatherapi.com/v1/current.json?key=035bdd3ef4ca4483b8b175622221110&q=$locate&aqi=yes"
    val queue = Volley.newRequestQueue(context)
    val stringRequest = StringRequest(
        Request.Method.GET,
        url,
        {
                response ->

            Log.d("ml", "response: $response")
        },
        {
                error ->
            Log.d("ml", "error: $error")
        }
    )
    queue.add(stringRequest)
}*/