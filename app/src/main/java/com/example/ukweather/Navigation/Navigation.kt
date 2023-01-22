package com.example.ukweather

import android.content.Context
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ukweather.Navigation.Screen
import com.example.ukweather.getWeather.climate
import com.example.ukweather.layout.mainScreen.myScreen
import com.example.ukweather.layout.mainScreen.todayScreen
import com.example.ukweather.layout.mainScreen.weekScreen

@ExperimentalMaterialApi
@Composable
fun Navigation(context: Context, nowClim: MutableState<climate>, todayClimate: MutableState<ArrayList<climate>>, today: Int, month: String) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.currentScreen.route){
        composable(route = Screen.currentScreen.route){
            myScreen(context = context, nowClim = nowClim, navController)
        }
        composable(route = Screen.todayScreen.route){
            todayScreen(
                context = context,
                navController,
                nowClimate = nowClim,
                todayClimate = todayClimate,
                day = today,
                month = month
            )
        }
        composable(route = Screen.weekScreen.route){
            weekScreen(context = context, climateState = nowClim, navController)
        }
    }
}