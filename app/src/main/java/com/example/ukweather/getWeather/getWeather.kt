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
import kotlin.collections.ArrayList

fun getResult(locate:String, context: Context, nowClimate: MutableState<climate>, myDbManager: DbManager, whil: String, todayClimate: MutableState<ArrayList<climate>>){
    var url = "empty"
    val queue = Volley.newRequestQueue(context)

    myDbManager.openDb()
    var timeLastSave = myDbManager.readDbData(ConstanseDb.TIME_COLUMN_VALUE_CURRENT_FOR)

    val calendar: Calendar = GregorianCalendar.getInstance()
    val hoursNow = calendar.get(Calendar.HOUR_OF_DAY)
    val dayNow = calendar.get(Calendar.DAY_OF_WEEK)
    val weekNow = calendar.get(Calendar.WEEK_OF_YEAR)
    val minutesNow = calendar.get(Calendar.MINUTE)
    Log.d("ml", "hour n: $hoursNow")

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

                    //Default questions

                    val todayLastSave = myDbManager.readDbData(ConstanseDb.TODAY_TIME_TABLE_NAME)

                    //Functions

                    fun GetNewInfo(): ArrayList<climate> {
                        val jsonArray = obj.getJSONArray("list")


                        val howMuchShow = (24.0 - hoursNow) / 3
                        val result = Math.ceil(howMuchShow).toInt()
                        //Log.d("ml", "how much show after: $result")

                        val list = arrayListOf<JSONObject>()
                        val weatherList = arrayListOf<climate>()

                        for (i in 0..result - 1) {
                            list.add(jsonArray.getJSONObject(i))
                            
                            val newTodayItem = climate()
                            newTodayItem.temp = jsonArray.getJSONObject(i).getJSONObject("main").getInt("temp")
                            newTodayItem.feelslike = jsonArray.getJSONObject(i).getJSONObject("main").getInt("feels_like")

                            var weatherD = ""
                            var weatherIc = 0
                            val weatherIcon = jsonArray.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("icon")
                            when(weatherIcon){
                                "01d" -> {
                                    weatherIc = R.drawable._01d
                                    weatherD = context.getString(R.string.clearDay)
                                }
                                "01n" -> {
                                    weatherIc = R.drawable._01n
                                    weatherD = context.getString(R.string.clearNight)
                                }
                                "02d" -> {
                                    weatherIc = R.drawable._02d
                                    weatherD = context.getString(R.string.fewClouds)
                                }
                                "02n" -> {
                                    weatherIc = R.drawable._02n
                                    weatherD = context.getString(R.string.fewClouds)
                                }
                                "03d" -> {
                                    weatherIc = R.drawable._03d
                                    weatherD = context.getString(R.string.clouds)
                                }
                                "03n" -> {
                                    weatherIc = R.drawable._03d
                                    weatherD = context.getString(R.string.clouds)
                                }
                                "04d" -> {
                                    weatherIc = R.drawable._04d
                                    weatherD = context.getString(R.string.brokenClouds)
                                }
                                "04n" -> {
                                    weatherIc = R.drawable._04d
                                    weatherD = context.getString(R.string.brokenClouds)
                                }
                                "09d" -> {
                                    weatherIc = R.drawable._09d
                                    weatherD = context.getString(R.string.showerRain)
                                }
                                "09n" -> {
                                    weatherIc = R.drawable._09d
                                    weatherD = context.getString(R.string.showerRain)
                                }
                                "10d" -> {
                                    weatherIc = R.drawable._10d
                                    weatherD = context.getString(R.string.rain)
                                }
                                "10n" -> {
                                    weatherIc = R.drawable._10n
                                    weatherD = context.getString(R.string.rain)
                                }
                                "11d" -> {
                                    weatherIc = R.drawable._11d
                                    weatherD = context.getString(R.string.thunderstorm)
                                }
                                "11n" -> {
                                    weatherIc = R.drawable._11d
                                    weatherD = context.getString(R.string.thunderstorm)
                                }
                                "13d" -> {
                                    weatherIc = R.drawable._13d
                                    weatherD = context.getString(R.string.snow)
                                }
                                "13n" -> {
                                    weatherIc = R.drawable._13d
                                    weatherD = context.getString(R.string.snow)
                                }
                                "50d" -> {
                                    weatherIc = R.drawable._50d
                                    weatherD = context.getString(R.string.mist)
                                }
                                "50n" -> {
                                    weatherIc = R.drawable._50d
                                    weatherD = context.getString(R.string.mist)
                                }
                            }
                            newTodayItem.weather = weatherD
                            newTodayItem.humidity = Math.round(jsonArray.getJSONObject(i).getJSONObject("main").getString("humidity").toDouble()).toInt()
                            newTodayItem.pressure = Math.round(jsonArray.getJSONObject(i).getJSONObject("main").getString("pressure").toDouble()).toInt()
                            newTodayItem.icon = weatherIc

                            val weatherTime = jsonArray.getJSONObject(i).getString("dt_txt")

                            val regex = Regex("\\s\\d\\d:\\d\\d")
                            val weatherT = regex.find(weatherTime)?.value

                            newTodayItem.hour = weatherT.toString()

                            /*
                              val weatherTime = todayClimateOfThreeHours.getString("dt_txt")

                              val regex = Regex("\\s\\d\\d:\\d\\d")
                              val weatherT = regex.find(weatherTime)
                             */
                            
                            weatherList.add(newTodayItem)
                            Log.d("ml", "weather is: ${newTodayItem.temp}")
                            Log.d("ml", "weather is: ${newTodayItem.humidity }")
                            Log.d("ml", "weather is: ${newTodayItem.pressure}")
                            Log.d("ml", "weather is: ${newTodayItem.wind}")
                            Log.d("ml", "weather is: ${newTodayItem.weather}")
                            Log.d("ml", "weather is: ${newTodayItem.icon}")
                            Log.d("ml", "weather is: ${newTodayItem.feelslike}")
                        }

                        //todayClimate.value = list
                        return weatherList
                    }

                    //-------------INSPECTIONS-------------

                    if(todayLastSave != null){

                    } else{
                        val newWeather = GetNewInfo()
                        todayClimate.value = newWeather

                    }
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