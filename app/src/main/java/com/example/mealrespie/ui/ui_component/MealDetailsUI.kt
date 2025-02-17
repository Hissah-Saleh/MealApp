package com.example.mealrespie.ui.ui_component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.mealrespie.data.models.MealDetails

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
fun MealDetailsUI(modifier: Modifier = Modifier, result: MealDetails) {
    Column(modifier) {
        GlideImage(
            model = result.mealThumb,
            contentDescription = result.mealName,
            modifier = Modifier.fillMaxWidth(),
        )

        Text(result.mealName, fontSize = 30.sp)
        Text(result.instructions.orEmpty())
        Text(result.measure1.orEmpty() + "  " + result.ingredient1.orEmpty())
        Text(result.measure2.orEmpty() + "  " + result.ingredient2.orEmpty())
        Text(result.measure3.orEmpty() + "  " + result.ingredient3.orEmpty())
        Text(result.measure4.orEmpty() + "  " + result.ingredient4.orEmpty())
        Text(result.measure5.orEmpty() + "  " + result.ingredient5.orEmpty())
        Text(result.measure6.orEmpty() + "  " + result.ingredient6.orEmpty())
        Text(result.measure7.orEmpty() + "  " + result.ingredient7.orEmpty())
        Text(result.measure8.orEmpty() + "  " + result.ingredient8.orEmpty())
        Text(result.measure9.orEmpty() + "  " + result.ingredient9.orEmpty())
        Text(result.measure10.orEmpty() + "  " + result.ingredient10.orEmpty())
        Text(result.measure11.orEmpty() + "  " + result.ingredient11.orEmpty())
        Text(result.measure12.orEmpty() + "  " + result.ingredient12.orEmpty())
        Text(result.measure13.orEmpty() + "  " + result.ingredient13.orEmpty())
        Text(result.measure14.orEmpty() + "  " + result.ingredient14.orEmpty())
        Text(result.measure15.orEmpty() + "  " + result.ingredient15.orEmpty())
    }
}