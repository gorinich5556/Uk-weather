package com.example.ukweather.layout.mainScreen

import android.content.Context
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ukweather.Navigation.Screen
import com.example.ukweather.R
import com.example.ukweather.db.DbManager
import com.example.ukweather.getWeather.climate
import com.example.ukweather.ui.theme.*

@Composable
fun bottom(context: Context, climateState: MutableState<climate>, navController: NavController, myDbManager: DbManager) {
    val state = rememberScrollState()
    Log.d("ml", "climate: ${climateState.value.temp}")
    //myDbManager.currentTempUpdateToDb(climateState.value)

    Column() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .verticalScroll(state)

        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .background(invisible)
                    .alpha(0f)
            
            ) {

            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(shape = RoundedCornerShape(topEnd = 30.dp, topStart = 30.dp))
                    .background(blue2)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(7.dp)
                        .padding(horizontal = 32.dp)
                        .offset(0.dp, 10.dp)
                        .clip(shape = RoundedCornerShape(20.dp))
                        .background(white)
                ){

                }
                Column(
                    modifier = Modifier
                        .padding(start = 32.dp)
                ) {
                    Text(
                        text = context.getString(R.string.weather),
                        style = TextStyle(
                            color = white,
                            fontSize = 20.sp
                        ),
                        modifier = Modifier
                            .padding(top = 11.dp)
                    )
                    Text(
                        text = "${context.getString(R.string.weather_temp)} ${climateState.value.temp}°",
                        style = TextStyle(
                            color = white,
                            fontSize = 18.sp
                        ),
                        modifier = Modifier
                            .padding(top = 7.dp)
                    )
                    Text(
                        text = "${context.getString(R.string.weather_feelslike)} ${climateState.value.feelslike}°",
                        style = TextStyle(
                            color = white,
                            fontSize = 18.sp
                        ),
                        modifier = Modifier
                            .padding(top = 7.dp)
                    )
                    Text(
                        text = "${context.getString(R.string.weather_winter)} ${Math.round((climateState.value.wind.toDouble() / 3.6).toDouble()).toInt()}м/с",
                        style = TextStyle(
                            color = white,
                            fontSize = 18.sp
                        ),
                        modifier = Modifier
                            .padding(top = 7.dp)
                    )
                    Text(
                        text = "${context.getString(R.string.weather_weather)} ${climateState.value.weather}",
                        style = TextStyle(
                            color = white,
                            fontSize = 18.sp
                        ),
                        modifier = Modifier
                            .padding(top = 7.dp)
                    )
                    Text(
                        text = "${context.getString(R.string.weather_humidity)} ${climateState.value.humidity}%",
                        style = TextStyle(
                            color = white,
                            fontSize = 18.sp
                        ),
                        modifier = Modifier
                            .padding(top = 7.dp)
                    )
                    Text(
                        text = "${context.getString(R.string.weather_pressure)} ${climateState.value.pressure}мм",
                        style = TextStyle(
                            color = white,
                            fontSize = 18.sp
                        ),
                        modifier = Modifier
                            .padding(top = 7.dp)
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(blue1),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable {
                        navController.navigate(Screen.currentScreen.route)
                    }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.now),
                    contentDescription = "now",
                    modifier = Modifier
                        .size(25.dp)
                )
                Text(
                    style = TextStyle(color = white, fontSize = 16.sp),
                    text = "зараз"
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable {
                        navController.navigate(Screen.todayScreen.route)
                    }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.day),
                    contentDescription = "today",
                    modifier = Modifier
                        .size(25.dp)
                )
                Text(
                    style = TextStyle(color = white, fontSize = 16.sp),
                    text = "сьогодні"
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable {
                        navController.navigate(Screen.weekScreen.route)
                    }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.week),
                    contentDescription = "week",
                    modifier = Modifier
                        .size(25.dp)
                )
                Text(
                    style = TextStyle(color = white, fontSize = 16.sp),
                    text = "неділя"
                )
            }
        }
    }
}