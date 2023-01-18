package com.example.ukweather.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.ukweather.constanse.ConstanseDb

class DbHelper(context: Context) : SQLiteOpenHelper(context, ConstanseDb.DATABASE_NAME, null, ConstanseDb.DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(ConstanseDb.TIME_CREATE_TABLE)
        db?.execSQL(ConstanseDb.CURRENT_TEMP_CREATE_TABLE)
        db?.execSQL(ConstanseDb.TODAY_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(ConstanseDb.TIME_SQL_DELETE_TABLE)
        db?.execSQL(ConstanseDb.CURRENT_TEMP_SQL_DELETE_TABLE)
        db?.execSQL(ConstanseDb.TODAY_SQL_DELETE_TABLE)
        onCreate(db)
    }
}