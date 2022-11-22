package com.example.ukweather.Navigation

sealed class Screen(val route : String){
    object currentScreen : Screen("main_screen")
    object todayScreen : Screen("today_screen")
    object weekScreen : Screen("week_screen")
}
