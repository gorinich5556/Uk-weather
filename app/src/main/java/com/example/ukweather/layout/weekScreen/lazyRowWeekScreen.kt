package com.example.ukweather.layout.weekScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ukweather.getWeather.ClimateOfWeek
import com.example.ukweather.getWeather.climate
import com.example.ukweather.ui.theme.blue2

@Composable
fun lazyRowWeekScreen(listOfWeekClimate: MutableState<ArrayList<ClimateOfWeek>>) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.4f)
            //.height(250.dp)
            .border(BorderStroke(10.dp, blue2))
            .padding(start = 5.dp),
    ) {
        itemsIndexed(listOfWeekClimate.value) { _, item ->
            itemWeatherWeekScreen(item)
        }
    }
}