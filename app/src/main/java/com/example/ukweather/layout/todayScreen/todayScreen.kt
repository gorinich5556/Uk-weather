package com.example.ukweather.layout.mainScreen

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.ukweather.getWeather.climate
import com.example.ukweather.layout.common.navigationBar
import com.example.ukweather.layout.todayScreen.lazyRowForShowWeather
import com.example.ukweather.ui.theme.backgroundDarkBlue
import com.example.ukweather.ui.theme.backgroundLightBlue
import java.util.*

@ExperimentalMaterialApi
@Composable
fun todayScreen(context: Context, navController: NavController, nowClimate: MutableState<climate>, todayClimate: MutableState<ArrayList<climate>>, day: Int, month: String) {
    val gradientGrayWhite = Brush.verticalGradient(0f to backgroundDarkBlue, 1000f to backgroundLightBlue)
    val scaffoldState = rememberBottomSheetScaffoldState()

    Log.d("ml", "today climate from today screen: $todayClimate")

    Box(
        modifier = Modifier
            .fillMaxSize()
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
            sheetShape = RoundedCornerShape(topEnd = 30.dp, topStart = 30.dp),
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
                ){
                    lazyRowForShowWeather(todayClimate, context)
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .zIndex(2f),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Column() {
                        changeLocate(nowClimate)
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            BoxWithConstraints() {
                                if(this.maxHeight > 280.dp)
                                Text(
                                    text = "$day $month",
                                    fontSize = 20.sp,
                                    color = Color.White,
                                    modifier = Modifier
                                        .padding(top = 40.dp)

                                )
                            }
                        }
                    }
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
    }
}