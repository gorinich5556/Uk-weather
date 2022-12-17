package com.example.ukweather.layout.todayScreen

import android.content.Context
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ukweather.R
import com.example.ukweather.ui.theme.blue2
import com.example.ukweather.ui.theme.white
import java.util.*

@Preview(showBackground = false)
@Composable
fun lazyRowForShowWeather() {

    val test_list = arrayListOf<Int>(0, 1, 2,)
    Box(
        modifier = Modifier
            //.padding(bottom = 50.dp)
            .fillMaxWidth()
            .fillMaxHeight(1f),
        contentAlignment = Alignment.Center
    ) {
        Column() {
            BoxWithConstraints() {
                if (this.maxHeight >= 300.dp) {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.4f)
                            .border(BorderStroke(10.dp, blue2))
                            .padding(start = 5.dp),
                    ) {
                        itemsIndexed(test_list) { _, item ->
                            itemWeather()
                        }
                    }
                } else {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.5f)
                            .border(BorderStroke(20.dp, blue2))
                            .padding(start = 5.dp),
                    ) {
                        itemsIndexed(test_list) { _, item ->

                            itemWeather()

                        }
                    }
                }
            }
        }
    }
}
