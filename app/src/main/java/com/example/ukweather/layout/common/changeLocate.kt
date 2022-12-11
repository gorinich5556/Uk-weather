package com.example.ukweather.layout.mainScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ukweather.getWeather.climate
import com.example.ukweather.ui.theme.darkBlue
import com.example.ukweather.ui.theme.white

@Composable
fun changeLocate(climateState: MutableState<climate>){
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .padding(top = 20.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(20.dp))
                        .background(darkBlue)
                        .padding(3.dp),
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        style = TextStyle(color = white, fontSize = 20.sp),
                        textAlign = TextAlign.Center,
                        text = climateState.value.name,
                    )
                }
            }
}