package com.example.ukweather.constanse

import android.provider.BaseColumns

object ConstanseDb {
    const val DATABASE_NAME = "DbWeather.db"
    const val DATABASE_VERSION = 18


    const val TIME_TABLE_NAME = "time_to_modifier"
    const val TIME_COLUMN_NAME_FOR = "for"
    const val TIME_COLUMN_NAME_TIME = "time"
    const val TIME_COLUMN_NAME_DAY = "day"
    const val TIME_COLUMN_NAME_WEEK = "week"

    const val TIME_COLUMN_VALUE_CURRENT_FOR = "current"
    const val TIME_COLUMN_VALUE_TODAY_FOR = "today"

    const val TIME_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS $TIME_TABLE_NAME (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "$TIME_COLUMN_NAME_FOR TEXT," +
            "$TIME_COLUMN_NAME_TIME INT," +
            "$TIME_COLUMN_NAME_DAY INT," +
            "$TIME_COLUMN_NAME_WEEK INT)"

    const val TIME_SQL_DELETE_TABLE = "DROP TABLE IF EXISTS $TIME_TABLE_NAME"


    const val CURRENT_TEMP_TABLE_NAME = "current_temp"

    const val CURRENT_TEMP_COLUMN_NAME_TEMP = "temp"
    const val CURRENT_TEMP_COLUMN_NAME_TEMP_FEELS_LIKE = "feels_like"
    const val CURRENT_TEMP_COLUMN_NAME_WIND = "wind"
    const val CURRENT_TEMP_COLUMN_NAME_PRESSURE = "pressure"
    const val CURRENT_TEMP_COLUMN_NAME_HUMIDITY = "humidity"
    const val CURRENT_TEMP_COLUMN_NAME_WEATHER = "weather"
    const val CURRENT_TEMP_COLUMN_NAME_LOCATE_NAME = "locate"


    const val CURRENT_TEMP_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS $CURRENT_TEMP_TABLE_NAME (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "$CURRENT_TEMP_COLUMN_NAME_TEMP INT," +
            "$CURRENT_TEMP_COLUMN_NAME_TEMP_FEELS_LIKE INT," +
            "$CURRENT_TEMP_COLUMN_NAME_PRESSURE INT," +
            "$CURRENT_TEMP_COLUMN_NAME_HUMIDITY INT," +
            "$CURRENT_TEMP_COLUMN_NAME_WIND INT,"+
            "$CURRENT_TEMP_COLUMN_NAME_WEATHER TEXT,"+
            "$CURRENT_TEMP_COLUMN_NAME_LOCATE_NAME TEXT)"

    const val CURRENT_TEMP_SQL_DELETE_TABLE = "DROP TABLE IF EXISTS $CURRENT_TEMP_TABLE_NAME"


    //--------------------------TODAY WEATHER CONST------------------\\


    const val TODAY_TABLE_NAME = "today_weather"

    const val TODAY_COLUMN_NAME_TEMP = "temp"
    const val TODAY_COLUMN_NAME_TEMP_FEELS_LIKE = "feels_like"
    const val TODAY_COLUMN_NAME_WIND = "wind"
    const val TODAY_COLUMN_NAME_PRESSURE = "pressure"
    const val TODAY_COLUMN_NAME_HUMIDITY = "humidity"
    const val TODAY_COLUMN_NAME_WEATHER = "weather"
    const val TODAY_COLUMN_NAME_HOUR = "hour"
    const val TODAY_COLUMN_NAME_ICON = "icon"


    const val TODAY_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS $TODAY_TABLE_NAME (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "$TODAY_COLUMN_NAME_TEMP INT," +
            "$TODAY_COLUMN_NAME_TEMP_FEELS_LIKE INT," +
            "$TODAY_COLUMN_NAME_PRESSURE INT," +
            "$TODAY_COLUMN_NAME_HUMIDITY INT," +
            "$TODAY_COLUMN_NAME_WIND INT,"+
            "$TODAY_COLUMN_NAME_WEATHER TEXT,"+
            "$TODAY_COLUMN_NAME_ICON INT,"+
            "$TODAY_COLUMN_NAME_HOUR INT)"

    const val TODAY_SQL_DELETE_TABLE = "DROP TABLE IF EXISTS $TODAY_TABLE_NAME"

}