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
import com.example.ukweather.getWeather.climate
import com.example.ukweather.ui.theme.blue2
import com.example.ukweather.ui.theme.white
import org.json.JSONObject
import java.util.regex.Pattern

@Composable
fun itemWeather(todayClimateOfThreeHours: climate, context: Context) {

    val weatherIc = todayClimateOfThreeHours.icon
    val weatherD = todayClimateOfThreeHours.weather
    val weatherT = todayClimateOfThreeHours.hour




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
                    topPart(weatherIc, weatherD, weatherT)
                    bottomPart(boxWithConstraintsScope, context, todayClimateOfThreeHours)
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(180.dp)
                        .border(BorderStroke(3.dp, blue2))
                ) {
                    topPart(weatherIc, weatherD, weatherT)
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
fun bottomPart(boxWithConstraintsScope:  BoxWithConstraintsScope, context: Context, weather: climate){
    if(boxWithConstraintsScope.maxHeight >= 300.dp){
        Column(
            modifier = Modifier
                .padding(start = 10.dp, bottom = 10.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            param(context.getString(R.string.weather_temp), weather.temp.toString() + "°")
            param(context.getString(R.string.weather_feelslike), weather.feelslike.toString() + "°")
            param(context.getString(R.string.weather_winter), weather.wind.toString() + "м/с")
            param(context.getString(R.string.weather_humidity), weather.humidity.toString() + "%")
            param(context.getString(R.string.weather_pressure), weather.pressure.toString() + "мм")
        }
    }else{
        Column(
            modifier = Modifier
                .padding(start = 10.dp, bottom = 12.dp, top = 5.dp)
        ) {
            param(context.getString(R.string.weather_temp), weather.temp.toString() + "°")
            param(context.getString(R.string.weather_feelslike), weather.feelslike.toString() + "°")
            param(context.getString(R.string.weather_winter), weather.wind.toString() + "м/с")
            param(context.getString(R.string.weather_humidity), weather.humidity.toString() + "%")
            param(context.getString(R.string.weather_pressure), weather.pressure.toString() + "мм")
        }
    }
}