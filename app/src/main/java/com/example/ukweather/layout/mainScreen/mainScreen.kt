package com.example.ukweather.layout.mainScreen

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.ukweather.db.DbManager
import com.example.ukweather.getWeather.climate
import com.example.ukweather.getWeather.getLocation
import com.example.ukweather.layout.common.navigationBar
import com.example.ukweather.ui.theme.backgroundDarkBlue
import com.example.ukweather.ui.theme.backgroundLightBlue
import com.example.ukweather.ui.theme.blue2
import com.example.ukweather.ui.theme.invisible

@ExperimentalMaterialApi
@Composable
fun myScreen(context: Context, temper: MutableState<Int>, nowClim: MutableState<climate>, navController: NavController, myDbManager: DbManager) {
    val nowClimate = nowClim
    val gradientGrayWhite = Brush.verticalGradient(0f to backgroundDarkBlue, 1000f to backgroundLightBlue)
    var temp = temper
    val scaffoldState = rememberBottomSheetScaffoldState()
    Box(
        modifier = Modifier
            .background(gradientGrayWhite)
    ) {
        BottomSheetScaffold(
            modifier = Modifier
                .padding(bottom = 70.dp)
                .zIndex(0.5f),
            sheetContent = {
                Box(
                    modifier = Modifier
                        .zIndex(1f),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    bottom(context, nowClimate)
                }
            },
            sheetShape =RoundedCornerShape(topEnd = 30.dp, topStart = 30.dp),
            scaffoldState = scaffoldState,
            sheetPeekHeight = 50.dp,
        ){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(gradientGrayWhite)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .zIndex(2f),
                    contentAlignment = Alignment.Center,
                ) {
                    nowTemp(nowClimate)
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .zIndex(2f),
                    contentAlignment = Alignment.TopCenter
                ) {
                    changeLocate(nowClimate)
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(2f),
            contentAlignment = Alignment.BottomCenter
        ) {
            navigationBar(navController =  navController)
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(0.1f)
                .background(gradientGrayWhite)
        ) {
        }
    }
}