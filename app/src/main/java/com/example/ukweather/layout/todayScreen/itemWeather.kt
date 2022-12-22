package com.example.ukweather.layout.todayScreen

import android.content.Context
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ukweather.R
import com.example.ukweather.ui.theme.blue2
import com.example.ukweather.ui.theme.white
import org.json.JSONObject
import java.util.regex.Pattern

@Composable
fun itemWeather(todayClimateOfThreeHours: JSONObject, context: Context) {
    val weather = todayClimateOfThreeHours.getJSONArray("weather").getJSONObject(0)
    val weatherIcon = weather.getString("icon")
    val weatherDesc = weather.getString("main")
    val weatherTime = todayClimateOfThreeHours.getString("dt_txt")

    val regex = Regex("\\s\\d\\d:\\d\\d")
    val weatherT = regex.find(weatherTime)

    var weatherD = ""
    var weatherIc = 0
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

    BoxWithConstraints() {
        val boxWithConstraintsScope = this
            //Log.d("ml", "max height: ${boxWithConstraintsScope.maxHeight}")

            if (boxWithConstraintsScope.maxHeight <= 200.dp) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(180.dp)
                        .border(BorderStroke(3.dp, blue2))
                        .verticalScroll(rememberScrollState())
                ) {
                    topPart(weatherIc, weatherD, weatherT?.value)
                    bottomPart(boxWithConstraintsScope, context, todayClimateOfThreeHours)
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(180.dp)
                        .border(BorderStroke(3.dp, blue2))
                ) {
                    topPart(weatherIc, weatherD, weatherT?.value)
                    bottomPart(boxWithConstraintsScope, context, todayClimateOfThreeHours)
                }
            }
        }
}

@Composable
fun param(text: String, param: String){
    Text(
        text = "$text $param",
        style = TextStyle(
            color = white,
            fontSize = 19.sp
        ),
        modifier = Modifier
            .padding(top = 5.dp)
    )
}
@Composable
fun topPart(weatherIc: Int, weather: String, weatherT: String?){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .drawBehind {
                val borderSize = 6.dp.toPx()
                drawLine(
                    color = blue2,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = borderSize
                )
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = weatherIc),
            contentDescription = "weather image",
            modifier = Modifier
                .size(60.dp)
                .padding(top = 5.dp, start = 10.dp)
        )
        Column(
            modifier = Modifier
                .padding(start = 6.dp)
        ) {
            Text(
                text = weatherT!!,
                style = TextStyle(
                    color = white,
                    fontSize = 22.sp
                ),
                modifier = Modifier
                    .padding(top = 5.dp)
            )
            Text(
                text = weather,
                style = TextStyle(
                    color = white,
                    fontSize = 20.sp
                ),
                modifier = Modifier
                    .offset(y = -3.dp)
            )
        }
    }
}

@Composable
fun bottomPart(boxWithConstraintsScope:  BoxWithConstraintsScope, context: Context, weather: JSONObject){
    if(boxWithConstraintsScope.maxHeight >= 300.dp){
        Column(
            modifier = Modifier
                .padding(start = 10.dp, bottom = 10.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            param(context.getString(R.string.weather_temp), (Math.round(weather.getJSONObject("main").getString("temp").toDouble())).toString() + "°")
            param(context.getString(R.string.weather_feelslike), (Math.round(weather.getJSONObject("main").getString("feels_like").toDouble())).toString() + "°")
            param(context.getString(R.string.weather_winter), (Math.round(weather.getJSONObject("wind").getString("speed").toDouble())).toString() + "м/с")
            param(context.getString(R.string.weather_humidity), (Math.round(weather.getJSONObject("main").getString("humidity").toDouble())).toString() + "%")
            param(context.getString(R.string.weather_pressure), (Math.round(weather.getJSONObject("main").getString("pressure").toDouble())).toString() + "мм")
        }
    }else{
        Column(
            modifier = Modifier
                .padding(start = 10.dp, bottom = 12.dp, top = 5.dp)
        ) {
            param(context.getString(R.string.weather_temp), (Math.round(weather.getJSONObject("main").getString("temp").toDouble())).toString() + "°")
            param(context.getString(R.string.weather_feelslike), (Math.round(weather.getJSONObject("main").getString("feels_like").toDouble())).toString() + "°")
            param(context.getString(R.string.weather_winter), (Math.round(weather.getJSONObject("wind").getString("speed").toDouble())).toString() + "м/с")
            param(context.getString(R.string.weather_humidity), (Math.round(weather.getJSONObject("main").getString("humidity").toDouble())).toString() + "%")
            param(context.getString(R.string.weather_pressure), (Math.round(weather.getJSONObject("main").getString("pressure").toDouble())).toString() + "мм")
        }
    }
}