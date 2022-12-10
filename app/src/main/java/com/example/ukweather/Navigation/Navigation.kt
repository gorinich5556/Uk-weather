package com.example.ukweather

import android.content.Context
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ukweather.Navigation.Screen
import com.example.ukweather.db.DbManager
import com.example.ukweather.getWeather.climate
import com.example.ukweather.getWeather.getLocation
import com.example.ukweather.layout.mainScreen.myScreen
import com.example.ukweather.layout.mainScreen.todayScreen
import com.example.ukweather.layout.mainScreen.weekScreen
@ExperimentalMaterialApi
@Composable
fun Navigation(context: Context, temper: MutableState<Int>, nowClim: MutableState<climate>, dbManager: DbManager) {
    val contextM = context
    var temp = temper
    val nowClimate = nowClim
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.currentScreen.route){
        composable(route = Screen.currentScreen.route){
            myScreen(context = contextM, temper = temp, nowClim = nowClimate, navController, myDbManager = dbManager)
        }
        composable(route = Screen.todayScreen.route){
            todayScreen(context = contextM, climateState = nowClim, navController, myDbManager = dbManager, nowClimate = nowClimate)
        }
        composable(route = Screen.weekScreen.route){
            weekScreen(context = contextM, climateState = nowClim, navController, myDbManager = dbManager)
        }
    }
}