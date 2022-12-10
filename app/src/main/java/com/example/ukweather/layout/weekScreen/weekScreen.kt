package com.example.ukweather.layout.mainScreen

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.ukweather.db.DbManager
import com.example.ukweather.getWeather.climate
@ExperimentalMaterialApi
@Composable
fun weekScreen(context: Context, climateState: MutableState<climate>, navController: NavController, myDbManager: DbManager){
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(text = "hello from week screen")
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ){
            bottom(context = context, climateState = climateState)
        }
    }
}