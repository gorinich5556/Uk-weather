package com.example.ukweather.layout.weekScreen

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ukweather.R
import com.example.ukweather.getWeather.ClimateOfWeek
import com.example.ukweather.getWeather.climate
import com.example.ukweather.ui.theme.blue2
import com.example.ukweather.ui.theme.darkBlue


@Composable
fun itemWeatherWeekScreen(climateItem: ClimateOfWeek) {
    val weatherImage = climateItem.icon
    val weatherData = climateItem.date
    val weatherMonth = climateItem.month

    val weather = climateItem.weather

    val maxTemp = climateItem.maxTemp
    val minTemp = climateItem.minTemp

    val maxWind = climateItem.wind
    val minWind = climateItem.wind
    BoxWithConstraints() {
        if(this.maxHeight < 250.dp) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(180.dp)
                    .verticalScroll(rememberScrollState())
                    .drawBehind {
                        val borderSize = 6.dp.toPx()
                        drawLine(
                            color = blue2,
                            start = Offset(size.width, 0f),
                            end = Offset(size.width, size.height),
                            strokeWidth = borderSize
                        )
                    }
                    .padding(top = 4.dp),

                ) {
                itemWeatherWeekScreenTopPart(
                    weatherImage = weatherImage,
                    weatherData = weatherData,
                    weatherMonth = weatherMonth,
                    weather = weather
                )
                itemWeatherWeekScreenTempPart(
                    minTemp = minTemp,
                    maxTemp = maxTemp
                )
                itemWeatherWeekScreenWindPart(
                    maxWind = maxWind,
                    minWind = minWind
                )
                itemWeatherWeekScreenBottomPart()
            }
        }else{
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(180.dp)
                    //.verticalScroll(rememberScrollState())
                    .drawBehind {
                        val borderSize = 6.dp.toPx()
                        drawLine(
                            color = blue2,
                            start = Offset(size.width, 0f),
                            end = Offset(size.width, size.height),
                            strokeWidth = borderSize
                        )
                    }
                    .padding(top = 4.dp),

                ) {
                itemWeatherWeekScreenTopPart(
                    weatherImage = weatherImage,
                    weatherData = weatherData,
                    weatherMonth = weatherMonth,
                    weather = weather
                )
                itemWeatherWeekScreenTempPart(
                    minTemp = minTemp,
                    maxTemp = maxTemp
                )
                itemWeatherWeekScreenWindPart(
                    maxWind = maxWind,
                    minWind = minWind
                )
                itemWeatherWeekScreenBottomPart()
            }
        }
    }
}

@Composable
fun paramWeekScreen(paramName: String, paramValue: Int, paramUnit: String, size: Int = 17){
    Text(
        text = "$paramName: $paramValue$paramUnit",
        fontSize = size.sp,
        color = Color.White
    )
}

@Composable
fun itemWeatherWeekScreenTopPart(weatherImage: Int, weatherData: Int, weatherMonth: String, weather: String){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.25f)
            .drawBehind {
                val borderSize = 6.dp.toPx()
                drawLine(
                    color = blue2,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = borderSize
                )
            }
            .padding(horizontal = 3.dp)
            .padding(bottom = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = weatherImage),
            contentDescription = "weather image",
            modifier = Modifier
                .size(60.dp)
                .padding(top = 5.dp)
        )
        Column(
            modifier = Modifier
                .padding(start = 5.dp)
        ) {
            Text(
                text = "$weatherData $weatherMonth",
                fontSize = 20.sp,
                color = Color.White
            )
            Text(
                text = weather,
                fontSize = 17.sp,
                color = Color.White
            )
        }
    }
}

@Composable
fun itemWeatherWeekScreenTempPart(minTemp: Int, maxTemp: Int){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.35f)
            .drawBehind {
                val borderSize = 6.dp.toPx()
                drawLine(
                    color = blue2,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = borderSize
                )
            }
            .padding(5.dp)
            .padding(horizontal = 3.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "температура:",
            fontSize = 20.sp,
            color = Color.White
        )
        Row(
            modifier = Modifier
                .padding(top = 3.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            paramWeekScreen(paramName = "макс", paramValue = maxTemp, paramUnit ="°")
            paramWeekScreen(paramName = "мін", paramValue = minTemp, paramUnit ="°")
        }
    }
}

@Composable
fun itemWeatherWeekScreenWindPart(maxWind: Int, minWind: Int){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f)
            .drawBehind {
                val borderSize = 6.dp.toPx()
                drawLine(
                    color = blue2,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = borderSize
                )
            }
            .padding(5.dp)
            .padding(horizontal = 3.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "вітер:",
            fontSize = 20.sp,
            color = Color.White
        )
        Row(
            modifier = Modifier
                .padding(top = 3.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            paramWeekScreen(paramName = "макс", paramValue = maxWind, paramUnit ="м/с", size = 15)
            paramWeekScreen(paramName = "мін", paramValue = minWind, paramUnit ="м/с", size = 15)
        }
    }
}

@Composable
fun itemWeatherWeekScreenBottomPart(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(1f)
            .padding(top = 15.dp, bottom = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp)
                .clip(shape = RoundedCornerShape(20.dp))
                .background(darkBlue)
                .padding(bottom = 4.dp, top = 3.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "детальніше",
                fontSize = 17.sp,
                color = Color.White
            )
        }
    }
}