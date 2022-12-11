package com.example.ukweather.layout.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.ukweather.Navigation.Screen
import com.example.ukweather.R
import com.example.ukweather.ui.theme.blue1
import com.example.ukweather.ui.theme.white

@Composable
fun navigationBar(navController: NavController,) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(blue1)
            .zIndex(2f),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clickable {
                    navController.navigate(Screen.currentScreen.route)
                }
        ) {
            Image(
                painter = painterResource(id = R.drawable.now),
                contentDescription = "now",
                modifier = Modifier
                    .size(25.dp)
            )
            Text(
                style = TextStyle(color = white, fontSize = 16.sp),
                text = "зараз"
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clickable {
                    navController.navigate(Screen.todayScreen.route)
                }
        ) {
            Image(
                painter = painterResource(id = R.drawable.day),
                contentDescription = "today",
                modifier = Modifier
                    .size(25.dp)
            )
            Text(
                style = TextStyle(color = white, fontSize = 16.sp),
                text = "сьогодні"
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clickable {
                    navController.navigate(Screen.weekScreen.route)
                }
        ) {
            Image(
                painter = painterResource(id = R.drawable.week),
                contentDescription = "week",
                modifier = Modifier
                    .size(25.dp)
            )
            Text(
                style = TextStyle(color = white, fontSize = 16.sp),
                text = "неділя"
            )
        }
    }
}