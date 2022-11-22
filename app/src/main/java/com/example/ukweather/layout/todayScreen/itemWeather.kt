package com.example.ukweather.layout.todayScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ukweather.R
import com.example.ukweather.ui.theme.blue2
import com.example.ukweather.ui.theme.white

@Preview
@Composable
fun itemWeather() {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(180.dp)
            .border(BorderStroke(3.dp, blue2))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .drawBehind {
                    val borderSize = 6.dp.toPx()
                    drawLine(
                        color = blue2,
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = borderSize
                    )
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.sunny),
                contentDescription = "weather image",
                modifier = Modifier
                    .size(60.dp)
                    .padding(top = 5.dp, start = 10.dp)
            )
            Column(
                modifier = Modifier
                    .padding(start = 6.dp)
            ) {
                Text(
                    text = "00:00",
                    style = TextStyle(
                        color = white,
                        fontSize = 22.sp
                    ),
                    modifier = Modifier
                        .padding(top = 5.dp)
                )
                Text(
                    text = "сонячно",
                    style = TextStyle(
                        color = white,
                        fontSize = 20.sp
                    ),
                    modifier = Modifier
                        .offset(y = -3.dp)
                )
            }
        }
        Column(
            modifier = Modifier
                .padding(start = 10.dp)
        ) {
            param("температура", "-1")
            param("відчувається", "-1")
            param("вітер", "1м/с")
            param("вологість", "80%")
            param("тиск", "1002мм")
        }
    }
}

@Composable
fun param(text: String, param: String){
    Text(
        text = "$text: $param",
        style = TextStyle(
            color = white,
            fontSize = 19.sp
        ),
        modifier = Modifier
            .padding(top = 5.dp)
    )
}