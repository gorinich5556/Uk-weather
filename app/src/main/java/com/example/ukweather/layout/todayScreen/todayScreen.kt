package com.example.ukweather.layout.mainScreen

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.ukweather.db.DbManager
import com.example.ukweather.getWeather.climate
import com.example.ukweather.layout.todayScreen.lazyRowForShowWeather
import com.example.ukweather.ui.theme.backgroundDarkBlue
import com.example.ukweather.ui.theme.backgroundLightBlue

@Composable
fun todayScreen(context: Context, climateState: MutableState<climate>, navController: NavController, myDbManager: DbManager, nowClimate: MutableState<climate>) {
    val gradientGrayWhite = Brush.verticalGradient(0f to backgroundDarkBlue, 1000f to backgroundLightBlue)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientGrayWhite)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(2f),
            contentAlignment = Alignment.BottomCenter
        ){
            bottom(context = context, climateState = climateState, navController = navController, myDbManager)
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
                .fillMaxSize()
        ){
            lazyRowForShowWeather()
        }
    }
}