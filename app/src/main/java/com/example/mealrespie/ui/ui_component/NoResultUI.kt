package com.example.mealrespie.ui.ui_component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.meal.R

@Composable
fun NoResultUI(modifier: Modifier= Modifier){

    Box(modifier, contentAlignment = Alignment.Center) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painterResource(R.drawable.computer_fail_svgrepo_com),
                contentDescription = "no_internet_error"

            )

            Text(LocalContext.current.getString(R.string.no_result))

        }
    }
}