package com.example.ukweather.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import com.example.ukweather.constanse.ConstanseDb
import com.example.ukweather.getWeather.climate

class DbManager(context: Context) {
    val myDbHelper = DbHelper(context)
    var db : SQLiteDatabase? = null

    fun openDb(){
        db = myDbHelper.writableDatabase
    }
    fun insertToDb(use: String, time: Int, day: Int, week: Int){
        val values = ContentValues().apply {
            put(ConstanseDb.TIME_COLUMN_NAME_FOR, use)
            put(ConstanseDb.TIME_COLUMN_NAME_TIME, time)
            put(ConstanseDb.TIME_COLUMN_NAME_DAY, day)
            put(ConstanseDb.TIME_COLUMN_NAME_WEEK, week)
        }
        db?.insert(ConstanseDb.TIME_TABLE_NAME, null, values)
    }
    fun readDbData(requestFor: String): timeNow?{
        var time: timeNow? = null

        val selection = "${ConstanseDb.TIME_COLUMN_NAME_FOR} like ?"

        val cursor = db?.query(ConstanseDb.TIME_TABLE_NAME, null, selection,
            arrayOf(requestFor), null, null, null)

        while (cursor?.moveToNext()!!){
            time = timeNow()

            time?.time = cursor.getInt(cursor.getColumnIndexOrThrow(ConstanseDb.TIME_COLUMN_NAME_TIME))
            time?.week = cursor.getInt(cursor.getColumnIndexOrThrow(ConstanseDb.TIME_COLUMN_NAME_WEEK))
            time?.id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"))
            time?.day = cursor.getInt(cursor.getColumnIndexOrThrow(ConstanseDb.TIME_COLUMN_NAME_DAY))
            time?.fore = cursor.getString(cursor.getColumnIndexOrThrow(ConstanseDb.TIME_COLUMN_NAME_FOR))
        }

        cursor.close()
        return time
    }
    fun updateDataToDb(timeNew: timeNow){
        val selection = BaseColumns._ID + "=${timeNew.id}"
        val values = ContentValues().apply {
            put(ConstanseDb.TIME_COLUMN_NAME_FOR, timeNew.fore)
            put(ConstanseDb.TIME_COLUMN_NAME_TIME, timeNew.time)
            put(ConstanseDb.TIME_COLUMN_NAME_DAY, timeNew.day)
            put(ConstanseDb.TIME_COLUMN_NAME_WEEK, timeNew.week)
        }
        db?.update(ConstanseDb.TIME_TABLE_NAME, values, selection, null)
    }
    fun closeDb(){
        myDbHelper.close()
    }


    /*---------------------CURRENT TEMP----------------------*/

    fun currentTempInsertToDb(climate: climate){
        val values = ContentValues().apply {
            put(ConstanseDb.CURRENT_TEMP_COLUMN_NAME_TEMP, climate.temp)
            put(ConstanseDb.CURRENT_TEMP_COLUMN_NAME_TEMP_FEELS_LIKE, climate.feelslike)
            put(ConstanseDb.CURRENT_TEMP_COLUMN_NAME_WIND, climate.wind)
            put(ConstanseDb.CURRENT_TEMP_COLUMN_NAME_PRESSURE, climate.pressure)
            put(ConstanseDb.CURRENT_TEMP_COLUMN_NAME_HUMIDITY, climate.humidity)
            put(ConstanseDb.CURRENT_TEMP_COLUMN_NAME_WEATHER, climate.weather)
            put(ConstanseDb.CURRENT_TEMP_COLUMN_NAME_LOCATE_NAME, climate.name)
        }
        db?.insert(ConstanseDb.CURRENT_TEMP_TABLE_NAME, null, values)
    }

    fun currentTempReadDbData(): climate{
        var cl = climate()

        val cursor = db?.query(ConstanseDb.CURRENT_TEMP_TABLE_NAME, null, null,
            null, null, null, null)

        while (cursor?.moveToNext()!!){
            cl.temp = cursor.getInt(cursor.getColumnIndexOrThrow(ConstanseDb.CURRENT_TEMP_COLUMN_NAME_TEMP))
            cl.feelslike = cursor.getInt(cursor.getColumnIndexOrThrow(ConstanseDb.CURRENT_TEMP_COLUMN_NAME_TEMP_FEELS_LIKE))
            cl.wind = cursor.getInt(cursor.getColumnIndexOrThrow(ConstanseDb.CURRENT_TEMP_COLUMN_NAME_WIND))
            cl.pressure = cursor.getInt(cursor.getColumnIndexOrThrow(ConstanseDb.CURRENT_TEMP_COLUMN_NAME_PRESSURE))
            cl.humidity = cursor.getInt(cursor.getColumnIndexOrThrow(ConstanseDb.CURRENT_TEMP_COLUMN_NAME_HUMIDITY))
            cl.weather = cursor.getString(cursor.getColumnIndexOrThrow(ConstanseDb.CURRENT_TEMP_COLUMN_NAME_WEATHER))
            cl.name = cursor.getString(cursor.getColumnIndexOrThrow(ConstanseDb.CURRENT_TEMP_COLUMN_NAME_LOCATE_NAME))
            cl.id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"))
        }

        cursor.close()
        return cl
    }
    fun currentTempUpdateToDb(newClimate: climate){
        val selection = BaseColumns._ID + "=${newClimate.id}"

        val values = ContentValues().apply {
            put(ConstanseDb.CURRENT_TEMP_COLUMN_NAME_TEMP, newClimate.temp)
            put(ConstanseDb.CURRENT_TEMP_COLUMN_NAME_TEMP_FEELS_LIKE, newClimate.feelslike)
            put(ConstanseDb.CURRENT_TEMP_COLUMN_NAME_WIND, newClimate.wind)
            put(ConstanseDb.CURRENT_TEMP_COLUMN_NAME_PRESSURE, newClimate.pressure)
            put(ConstanseDb.CURRENT_TEMP_COLUMN_NAME_HUMIDITY, newClimate.humidity)
            put(ConstanseDb.CURRENT_TEMP_COLUMN_NAME_WEATHER, newClimate.weather)
            put(ConstanseDb.CURRENT_TEMP_COLUMN_NAME_LOCATE_NAME, newClimate.name)

        }
        db?.update(ConstanseDb.CURRENT_TEMP_TABLE_NAME, values, selection, null)
    }

    //--------------------------TODAY WEATHER------------------//



    fun todayWeatherInsertToDb(climate: climate){
        val values = ContentValues().apply {
            put(ConstanseDb.TODAY_COLUMN_NAME_TEMP, climate.temp)
            put(ConstanseDb.TODAY_COLUMN_NAME_TEMP_FEELS_LIKE, climate.feelslike)
            put(ConstanseDb.TODAY_COLUMN_NAME_WIND, climate.wind)
            put(ConstanseDb.TODAY_COLUMN_NAME_PRESSURE, climate.pressure)
            put(ConstanseDb.TODAY_COLUMN_NAME_HUMIDITY, climate.humidity)
            put(ConstanseDb.TODAY_COLUMN_NAME_WEATHER, climate.weather)
            put(ConstanseDb.TODAY_COLUMN_NAME_HOUR, climate.hour)
        }
        db?.insert(ConstanseDb.TODAY_TABLE_NAME, null, values)
    }

    fun todayWeatherReadDbData(): ArrayList<climate>{
        val list = ArrayList<climate>()

        val cursor = db?.query(ConstanseDb.TODAY_TABLE_NAME, null, null,
            null, null, null, null)

        while (cursor?.moveToNext()!!){
            val cl = climate()
            cl.temp = cursor.getInt(cursor.getColumnIndexOrThrow(ConstanseDb.TODAY_COLUMN_NAME_TEMP))
            cl.feelslike = cursor.getInt(cursor.getColumnIndexOrThrow(ConstanseDb.TODAY_COLUMN_NAME_TEMP_FEELS_LIKE))
            cl.wind = cursor.getInt(cursor.getColumnIndexOrThrow(ConstanseDb.TODAY_COLUMN_NAME_WIND))
            cl.pressure = cursor.getInt(cursor.getColumnIndexOrThrow(ConstanseDb.TODAY_COLUMN_NAME_PRESSURE))
            cl.humidity = cursor.getInt(cursor.getColumnIndexOrThrow(ConstanseDb.TODAY_COLUMN_NAME_HUMIDITY))
            cl.weather = cursor.getString(cursor.getColumnIndexOrThrow(ConstanseDb.TODAY_COLUMN_NAME_WEATHER))
            cl.hour = cursor.getString(cursor.getColumnIndexOrThrow(ConstanseDb.TODAY_COLUMN_NAME_HOUR))
            cl.id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"))

            list.add(cl)
        }

        cursor.close()
        return list
    }
    fun todayWeatherUpdateToDb(newClimate: climate){
        val selection = BaseColumns._ID + "=${newClimate.id}"

        val values = ContentValues().apply {
            put(ConstanseDb.TODAY_COLUMN_NAME_TEMP, newClimate.temp)
            put(ConstanseDb.TODAY_COLUMN_NAME_TEMP_FEELS_LIKE, newClimate.feelslike)
            put(ConstanseDb.TODAY_COLUMN_NAME_WIND, newClimate.wind)
            put(ConstanseDb.TODAY_COLUMN_NAME_PRESSURE, newClimate.pressure)
            put(ConstanseDb.TODAY_COLUMN_NAME_HUMIDITY, newClimate.humidity)
            put(ConstanseDb.TODAY_COLUMN_NAME_WEATHER, newClimate.weather)
            put(ConstanseDb.TODAY_COLUMN_NAME_HOUR, newClimate.hour)

        }
        db?.update(ConstanseDb.TODAY_TABLE_NAME, values, selection, null)
    }

}