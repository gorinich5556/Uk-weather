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
import com.example.ukweather.getWeather.ClimateOfWeek
import com.example.ukweather.getWeather.climate
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs

fun getResult(locate:String, context: Context, nowClimate: MutableState<climate>, myDbManager: DbManager, whil: String, todayClimate: MutableState<ArrayList<climate>>, daysOfWeekClimate: MutableState<ArrayList<ClimateOfWeek>>){
    var url = "empty"
    val queue = Volley.newRequestQueue(context)

    myDbManager.openDb()

    val calendar: Calendar = GregorianCalendar.getInstance()
    val hoursNow = calendar.get(Calendar.HOUR_OF_DAY)
    val dayNow = calendar.get(Calendar.DAY_OF_WEEK)
    val weekNow = calendar.get(Calendar.WEEK_OF_YEAR)
    val dayOfMouth = calendar.get(Calendar.DAY_OF_MONTH)
    val newTime = timeNow()
    newTime.week = weekNow
    newTime.day = dayNow
    newTime.time = hoursNow
    Log.d("ml", "hour n: $hoursNow")

    when(whil){
        ConstanseWeather.CURRENT_WEATHER ->{
            url = "https://api.openweathermap.org/data/2.5/weather?$locate&units=metric&appid=${ConstanseWeather.API_KEY}&lang=ua"

        }
        ConstanseWeather.FORECAST_OF_DAY ->{
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
                    newTime.id = 1
                    newTime.fore = ConstanseDb.TIME_COLUMN_VALUE_CURRENT_FOR

                    val timeLastSave = myDbManager.readDbData(ConstanseDb.TIME_COLUMN_VALUE_CURRENT_FOR)

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
                                if ((hoursNow - timeLastSave.time) >= 1) {
                                    nowClimate.value = newClimate
                                    myDbManager.updateDataToDb(newTime)
                                    myDbManager.currentTempUpdateToDb(newClimate)
                                }
                            } else {
                                nowClimate.value = newClimate
                                myDbManager.updateDataToDb(newTime)
                                myDbManager.currentTempUpdateToDb(newClimate)
                            }
                        } else {
                            nowClimate.value = newClimate
                            myDbManager.updateDataToDb(newTime)
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

                    //common quest
                    val jsonArray = obj.getJSONArray("list")

                    //--------------------------------FOR TODAY--------------------------

                    //Default quest

                    newTime.fore = ConstanseDb.TIME_COLUMN_VALUE_TODAY_FOR
                    val todayLastSave = myDbManager.readDbData(ConstanseDb.TIME_COLUMN_VALUE_TODAY_FOR)
                    Log.d("ml", "weather is: $obj")

                    //Functions

                    fun GetNewInfo(): ArrayList<climate> {
                        val list = arrayListOf<JSONObject>()
                        val weatherList = arrayListOf<climate>()

                        for(i in 0..jsonArray.length()-1){

                            /** Inspection date */

                            val item = jsonArray.getJSONObject(i).getString("dt_txt")
                            Log.d("ml","date: $item")
                            val regex = Regex("\\d\\d ")
                            val date = regex.find(item)?.value?.replace(" ", "")
                            Log.d("ml", "day: $date")
                            if(date?.toInt() == dayOfMouth + 1) break


                            /** The following code fills the list with items */

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

                            val regexer = Regex("\\s\\d\\d:\\d\\d")
                            val weatherT = regexer.find(weatherTime)?.value

                            newTodayItem.hour = weatherT.toString()


                            weatherList.add(newTodayItem)

                        }


                        //todayClimate.value = list
                        return weatherList
                    }

                    fun updateInfoToday(){
                        Log.d("ml","UPDATE INFO!!!")
                        val newWeather = GetNewInfo()
                        todayClimate.value = newWeather
                        myDbManager.deleteAllFromTable(ConstanseDb.TODAY_TABLE_NAME)
                        for(todayWeatherItem in newWeather){
                            myDbManager.todayWeatherInsertToDb(todayWeatherItem)
                            Log.d("ml", "dhghf: $todayWeatherItem")
                        }
                        myDbManager.updateDataToDb(newTime)
                    }

                    //-------------INSPECTIONS-------------

                    if(todayLastSave != null){
                        if(todayLastSave.week == weekNow){
                            if(todayLastSave.day == dayNow){
                                if(abs(hoursNow - todayLastSave.time) >= 3){
                                    updateInfoToday()
                                }
                            }else{
                                updateInfoToday()
                            }
                        }else{
                            updateInfoToday()
                        }
                    } else{
                        val newWeather = GetNewInfo()
                        todayClimate.value = newWeather
                        for(todayWeatherItem in newWeather){
                            myDbManager.todayWeatherInsertToDb(todayWeatherItem)
                        }
                        myDbManager.insertToDb(ConstanseDb.TIME_COLUMN_VALUE_TODAY_FOR, hoursNow, dayNow, weekNow)
                    }

                    //-------------------------------------------FOR WEEK--------------------

                    //Default quest

                    newTime.fore = ConstanseDb.TIME_COLUMN_VALUE_WEEK
                    val weekLastSave = myDbManager.readDbData(ConstanseDb.TIME_COLUMN_VALUE_WEEK)

                    //Functions

                    var typeDate = 0
                    val listData = arrayListOf<JSONObject>()
                    var a = 0

                    for(i in 0 until jsonArray.length()){

                        val jsonItem = jsonArray.getJSONObject(i)
                        val item = jsonItem.getString("dt_txt")
                        val regex = Regex("\\d\\d ")
                        val date = regex.find(item)?.value!!.replace(" ", "").toInt()
                        //Log.d("ml", "dateghjfgj is : $date")
                        //Log.d("ml", "dadj is : $datee")
                        if(typeDate == 0) {
                            typeDate = date
                            //Log.d("ml", "type date null but date: $date")
                            listData.add(jsonItem)
                        }else{
                            if(typeDate == date){
                                listData.add(jsonItem)
                            }else{
                                for(i in listData){
                                    /**-------The part for write a weather info to climate of week class*/

                                }
                                typeDate = 0
                                listData.clear()
                            }
                        }
                    }

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

            val timeLastSave = myDbManager.readDbData(ConstanseDb.TIME_COLUMN_VALUE_CURRENT_FOR)
            if (timeLastSave != null) {
                if (timeLastSave.week == weekNow) {
                    if (timeLastSave.day == dayNow) {
                        if ((hoursNow - timeLastSave.time) >= 1) {
                            queue.add(stringRequest)
                        } else {
                            val oldClimate = myDbManager.currentTempReadDbData()
                            Log.d("ml", "old temp is: ${oldClimate.temp}")
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
            val timeLastSave = myDbManager.readDbData(ConstanseDb.TIME_COLUMN_VALUE_TODAY_FOR)
            if (timeLastSave != null) {
                if (timeLastSave.week == weekNow) {
                    if (timeLastSave.day == dayNow) {
                        if (abs(hoursNow - timeLastSave.time) >= 3) {
                            queue.add(stringRequest)
                        } else {
                            Log.d("ml","old info")
                            val oldClimate = myDbManager.todayWeatherReadDbData()
                            todayClimate.value = oldClimate
                        }
                    } else {
                        Log.d("ml", "now day alredy")
                        queue.add(stringRequest)
                    }
                } else {
                    queue.add(stringRequest)
                }
            }else{
                queue.add(stringRequest)
            }
            queue.add(stringRequest)
        }
    }

    return
}
