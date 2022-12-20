package com.example.ukweather.layout.todayScreen

import android.content.Context
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ukweather.R
import com.example.ukweather.ui.theme.blue2
import com.example.ukweather.ui.theme.white
import org.json.JSONObject

@Composable
fun itemWeather(todayClimateOfThreeHours: JSONObject, context: Context) {
    val weatherIcon = todayClimateOfThreeHours.getJSONObject("weather").getString("icon")
    var weatherIc = 0
    /*when(weatherIcon){
        "01d" -> {
            weatherIc = R.drawable.sunny
        }
    }*/

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
                    topPart()
                    bottomPart(boxWithConstraintsScope, context, todayClimateOfThreeHours)
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(180.dp)
                        .border(BorderStroke(3.dp, blue2))
                ) {
                    topPart()
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
fun topPart(){
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
            painter = painterResource(id = R.drawable.sunny),
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
                text = "00:00",
                style = TextStyle(
                    color = white,
                    fontSize = 22.sp
                ),
                modifier = Modifier
                    .padding(top = 5.dp)
            )
            Text(
                text = "сонячно",
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
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .padding(start = 10.dp)
                .fillMaxSize(),
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