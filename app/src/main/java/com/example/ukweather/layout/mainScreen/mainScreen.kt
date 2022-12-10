package com.example.ukweather.layout.mainScreen

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.navigation.NavController
import com.example.ukweather.db.DbManager
import com.example.ukweather.getWeather.climate
import com.example.ukweather.getWeather.getLocation
import com.example.ukweather.ui.theme.backgroundDarkBlue
import com.example.ukweather.ui.theme.backgroundLightBlue
@ExperimentalMaterialApi
@Composable
fun myScreen(context: Context, temper: MutableState<Int>, nowClim: MutableState<climate>, navController: NavController, myDbManager: DbManager) {
    val nowClimate = nowClim
    val gradientGrayWhite = Brush.verticalGradient(0f to backgroundDarkBlue, 1000f to backgroundLightBlue)
    var temp = temper

    Box(
        modifier = Modifier
            .background(gradientGrayWhite)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            nowTemp(nowClimate)
        }
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            changeLocate(nowClimate)
        }
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            bottom(context, nowClimate, navController, myDbManager)
        }
    }
}