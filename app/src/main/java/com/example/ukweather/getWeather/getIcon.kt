package com.example.ukweather.getWeather

import android.content.Context
import com.example.ukweather.R

fun getIcon(iconName: String, context: Context):ArrayList<String> {
    var weatherD = ""
    var weatherIc = 0
    when(iconName){
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
    return arrayListOf(weatherIc.toString(), weatherD)
}