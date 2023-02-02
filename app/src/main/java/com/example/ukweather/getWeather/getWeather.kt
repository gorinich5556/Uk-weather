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
import com.example.ukweather.getWeather.getIcon
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs

fun getResult(locate:String, context: Context, nowClimate: MutableState<climate>, myDbManager: DbManager, whil: String, todayClimate: MutableState<ArrayList<climate>>, daysOfWeekClimate: MutableState<ArrayList<ClimateOfWeek>>) {
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

    when (whil) {
        ConstanseWeather.CURRENT_WEATHER -> {
            url =
                "https://api.openweathermap.org/data/2.5/weather?$locate&units=metric&appid=${ConstanseWeather.API_KEY}&lang=ua"

        }
        ConstanseWeather.FORECAST_OF_DAY -> {
            url =
                "https://api.openweathermap.org/data/2.5/forecast?$locate&units=metric&cnt=40&appid=${ConstanseWeather.API_KEY}&lang=ua"
        }

    }

    val stringRequest = StringRequest(
        Request.Method.GET,
        url,
        { response ->
            //Log.d("ml", "response: $response")

            val obj = JSONObject(response)

            when (whil) {
                ConstanseWeather.CURRENT_WEATHER -> {
                    newTime.id = 1
                    newTime.fore = ConstanseDb.TIME_COLUMN_VALUE_CURRENT_FOR

                    val timeLastSave =
                        myDbManager.readDbData(ConstanseDb.TIME_COLUMN_VALUE_CURRENT_FOR)

                    val tempF = (obj.getJSONObject("main")).getString("temp")
                    val tempFell = (obj.getJSONObject("main")).getString("feels_like")
                    val wind = (obj.getJSONObject("wind")).getString("speed")
                    val pressure = (obj.getJSONObject("main")).getString("pressure")
                    val humidity = (obj.getJSONObject("main")).getString("humidity")
                    val weather =
                        (obj.getJSONArray("weather")).getJSONObject(0).getString("description")
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
                    if (timeLastSave != null) {
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
                ConstanseWeather.FORECAST_OF_DAY -> {

                    //common quest
                    val jsonArray = obj.getJSONArray("list")

                    //--------------------------------FOR TODAY--------------------------

                    //Default quest

                    newTime.fore = ConstanseDb.TIME_COLUMN_VALUE_TODAY_FOR
                    val todayLastSave =
                        myDbManager.readDbData(ConstanseDb.TIME_COLUMN_VALUE_TODAY_FOR)
                    Log.d("ml", "weather is: $obj")

                    //Functions

                    fun GetNewInfo(): ArrayList<climate> {
                        val list = arrayListOf<JSONObject>()
                        val weatherList = arrayListOf<climate>()

                        for (i in 0..jsonArray.length() - 1) {

                            /** Inspection date */

                            val item = jsonArray.getJSONObject(i).getString("dt_txt")
                            Log.d("ml", "date: $item")
                            val regex = Regex("\\d\\d ")
                            val date = regex.find(item)?.value?.replace(" ", "")
                            Log.d("ml", "day: $date")
                            if (date?.toInt() == dayOfMouth + 1) break


                            /** The following code fills the list with items */

                            list.add(jsonArray.getJSONObject(i))

                            val newTodayItem = climate()
                            newTodayItem.temp =
                                jsonArray.getJSONObject(i).getJSONObject("main").getInt("temp")
                            newTodayItem.feelslike =
                                jsonArray.getJSONObject(i).getJSONObject("main")
                                    .getInt("feels_like")


                            val weatherIcon =
                                jsonArray.getJSONObject(i).getJSONArray("weather").getJSONObject(0)
                                    .getString("icon")
                            val weatherDescAndIc = getIcon(weatherIcon, context)
                            var weatherD = weatherDescAndIc[1]
                            var weatherIc = weatherDescAndIc[0].toInt()
                            newTodayItem.weather = weatherD
                            newTodayItem.humidity = Math.round(
                                jsonArray.getJSONObject(i).getJSONObject("main")
                                    .getString("humidity").toDouble()
                            ).toInt()
                            newTodayItem.pressure = Math.round(
                                jsonArray.getJSONObject(i).getJSONObject("main")
                                    .getString("pressure").toDouble()
                            ).toInt()
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

                    fun updateInfoToday() {
                        Log.d("ml", "UPDATE INFO!!!")
                        val newWeather = GetNewInfo()
                        todayClimate.value = newWeather
                        myDbManager.deleteAllFromTable(ConstanseDb.TODAY_TABLE_NAME)
                        for (todayWeatherItem in newWeather) {
                            myDbManager.todayWeatherInsertToDb(todayWeatherItem)
                            Log.d("ml", "dhghf: $todayWeatherItem")
                        }
                        myDbManager.updateDataToDb(newTime)
                    }

                    //-------------INSPECTIONS-------------

                    if (todayLastSave != null) {
                        if (todayLastSave.week == weekNow) {
                            if (todayLastSave.day == dayNow) {
                                if (abs(hoursNow - todayLastSave.time) >= 3) {
                                    updateInfoToday()
                                }
                            } else {
                                updateInfoToday()
                            }
                        } else {
                            updateInfoToday()
                        }
                    } else {
                        val newWeather = GetNewInfo()
                        todayClimate.value = newWeather
                        for (todayWeatherItem in newWeather) {
                            myDbManager.todayWeatherInsertToDb(todayWeatherItem)
                        }
                        myDbManager.insertToDb(
                            ConstanseDb.TIME_COLUMN_VALUE_TODAY_FOR,
                            hoursNow,
                            dayNow,
                            weekNow
                        )
                    }

                    //-------------------------------------------FOR WEEK--------------------

                    //Default quest

                    newTime.fore = ConstanseDb.TIME_COLUMN_VALUE_WEEK
                    val weekLastSave = myDbManager.readDbData(ConstanseDb.TIME_COLUMN_VALUE_WEEK)

                    //Functions

                    val listOfDays = arrayListOf<ClimateOfWeek>()

                    var typeDate = 0
                    val listData = arrayListOf<JSONObject>()
                    var a = 0

                    for (i in 0 until jsonArray.length()) {
                        a++

                        val jsonItem = jsonArray.getJSONObject(i)
                        val item = jsonItem.getString("dt_txt")
                        val regex = Regex("\\d\\d ")
                        val date = regex.find(item)?.value!!.replace(" ", "").toInt()
                        //Log.d("ml", "dateghjfgj is : $date")
                        //Log.d("ml", "dadj is : $datee")
                        if (typeDate == 0) {
                            typeDate = date
                            //Log.d("ml", "type date null but date: $date")
                            listData.add(jsonItem)
                        } else {
                            if (typeDate == date) {
                                listData.add(jsonItem)
                            } else {
                                val newInstanceDay = ClimateOfWeek()

                                val maxTemps = arrayListOf<Int>()
                                val minTemps = arrayListOf<Int>()
                                val winds = arrayListOf<Int>()
                                val descs = arrayListOf<String>()
                                val icons = arrayListOf<ArrayList<String>>()

                                //Get month
                                val itemDate = jsonItem.getString("dt_txt")
                                val regexMonth = Regex("-\\d\\d-")
                                val month = regexMonth.find(itemDate)!!.value.replace("-", "")
                                Log.d("ml", "month is: $month + $a")
                                var monthName = ""
                                when (month) {
                                    "01" -> monthName = context.getString(R.string.shortJanuary)
                                    "02" -> monthName = context.getString(R.string.shortFebruary)
                                    "03" -> monthName = context.getString(R.string.shortMarch)
                                    "04" -> monthName = context.getString(R.string.shortApril)
                                    "05" -> monthName = context.getString(R.string.shortMay)
                                    "06" -> monthName = context.getString(R.string.shortJune)
                                    "07" -> monthName = context.getString(R.string.shortJuly)
                                    "08" -> monthName = context.getString(R.string.shortAugust)
                                    "09" -> monthName = context.getString(R.string.shortSeptember)
                                    "10" -> monthName = context.getString(R.string.shortOctober)
                                    "11" -> monthName = context.getString(R.string.shortNovember)
                                    "12" -> monthName = context.getString(R.string.shortDecember)
                                }

                                for (i in listData) {
                                    /**-------The part for write a weather info to climate of week class*/
                                    maxTemps.add(
                                        Math.round(
                                            i.getJSONObject("main").getDouble("temp_max")
                                        ).toInt()
                                    )
                                    minTemps.add(
                                        Math.round(
                                            i.getJSONObject("main").getDouble("temp_min")
                                        ).toInt()
                                    )
                                    val iconAndDesc = getIcon(
                                        i.getJSONArray("weather").getJSONObject(0)
                                            .getString("icon"), context
                                    )
                                    winds.add(Math.round(i.getJSONObject("wind").getDouble("speed")).toInt())
                                    icons.add(iconAndDesc)
                                }
                                val maxTempOfDay = maxTemps.maxOrNull() ?: 0
                                val minTempOfDay = minTemps.minOrNull() ?: 0
                                val maxWind = winds.maxOrNull() ?: 0
                                val minWind = winds.minOrNull() ?: 0
                                var desc: String? = null
                                var icon = 0
                                for (i in icons) {
                                    if (i[1] == context.getString(R.string.showerRain) ||
                                        i[1] == context.getString(R.string.snow)
                                    ) {
                                        desc = i[1]
                                        icon = i[0].toInt()
                                        break
                                    } else if (i[1] == context.getString(R.string.rain)) {
                                        desc = i[1]
                                        icon = i[0].toInt()
                                        break
                                    } else if (i[1] == context.getString(R.string.clearDay)) {
                                        desc = i[1]
                                        icon = i[0].toInt()
                                        break
                                    }
                                }
                                if (desc == null) {
                                    val meanIndex = icons.size / 2
                                    desc = icons[meanIndex][1]
                                    icon = icons[meanIndex][0].toInt()
                                }
                                Log.d("ml", "desc of day is: $desc")

                                newInstanceDay.maxTemp = maxTempOfDay
                                newInstanceDay.minTemp = minTempOfDay
                                newInstanceDay.date = typeDate
                                newInstanceDay.month = monthName
                                newInstanceDay.weather = desc
                                newInstanceDay.icon = icon
                                newInstanceDay.maxWind = maxWind
                                newInstanceDay.minWind = minWind


                                listOfDays.add(newInstanceDay)
                                typeDate = 0
                                listData.clear()
                            }
                        }
                    }

                    daysOfWeekClimate.value = listOfDays

                }
            }
        },
        { error ->
            Log.d("ml", "error: $error")
        }
    )

    when (whil) {
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
            } else {
                queue.add(stringRequest)
            }
        }
        ConstanseWeather.FORECAST_OF_DAY -> {
            val timeLastSave = myDbManager.readDbData(ConstanseDb.TIME_COLUMN_VALUE_TODAY_FOR)
            if (timeLastSave != null) {
                if (timeLastSave.week == weekNow) {
                    if (timeLastSave.day == dayNow) {
                        if (abs(hoursNow - timeLastSave.time) >= 3) {
                            queue.add(stringRequest)
                        } else {
                            Log.d("ml", "old info")
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
            } else {
                queue.add(stringRequest)
            }
            queue.add(stringRequest)
        }
    }

    return
}
