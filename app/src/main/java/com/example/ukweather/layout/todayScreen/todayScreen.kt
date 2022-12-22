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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.ukweather.R
import com.example.ukweather.db.DbManager
import com.example.ukweather.getWeather.climate
import com.example.ukweather.layout.common.navigationBar
import com.example.ukweather.layout.todayScreen.lazyRowForShowWeather
import com.example.ukweather.ui.theme.backgroundDarkBlue
import com.example.ukweather.ui.theme.backgroundLightBlue
import org.json.JSONObject
import java.util.*

@ExperimentalMaterialApi
@Composable
fun todayScreen(context: Context, navController: NavController, nowClimate: MutableState<climate>, todayClimate: MutableState<ArrayList<JSONObject>>) {
    val gradientGrayWhite = Brush.verticalGradient(0f to backgroundDarkBlue, 1000f to backgroundLightBlue)
    val scaffoldState = rememberBottomSheetScaffoldState()
    val calendar: Calendar = GregorianCalendar.getInstance()
    val dayNow = calendar.get(Calendar.DAY_OF_MONTH)
    val monthNow = calendar.get(Calendar.MONTH)

    Log.d("ml", "today climate from today screen: $todayClimate")

    var monthName = ""
        when(monthNow){
            0 -> monthName = context.getString(R.string.January)
            1 -> monthName = context.getString(R.string.February)
            2 -> monthName = context.getString(R.string.March)
            3 -> monthName = context.getString(R.string.April)
            4 -> monthName = context.getString(R.string.May)
            5 -> monthName = context.getString(R.string.June)
            6 -> monthName = context.getString(R.string.July)
            7 -> monthName = context.getString(R.string.August)
            8 ->  monthName = context.getString(R.string.September)
            9 -> monthName = context.getString(R.string.October)
            10 -> monthName = context.getString(R.string.November)
            11 -> monthName = context.getString(R.string.December)
        }
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
                                    text = "$dayNow $monthName",
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