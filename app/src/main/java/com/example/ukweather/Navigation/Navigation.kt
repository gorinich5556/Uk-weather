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
import org.json.JSONObject

@ExperimentalMaterialApi
@Composable
fun Navigation(context: Context, nowClim: MutableState<climate>, todayClimate: MutableState<ArrayList<climate>>) {
    val contextM = context
    val nowClimate = nowClim
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.currentScreen.route){
        composable(route = Screen.currentScreen.route){
            myScreen(context = contextM, nowClim = nowClimate, navController)
        }
        composable(route = Screen.todayScreen.route){
            todayScreen(context = contextM, navController, nowClimate = nowClimate, todayClimate = todayClimate)
        }
        composable(route = Screen.weekScreen.route){
            weekScreen(context = contextM, climateState = nowClim, navController)
        }
    }
}