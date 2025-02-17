package com.example.mealrespie.data.models


import com.google.gson.annotations.SerializedName

data class CategoryMealsDTO(
    @SerializedName("meals")
    val meals: List<MealItem>?
)

data class MealItem(
    @SerializedName("strMeal")
    val mealName: String?,
    @SerializedName("strMealThumb")
    val mealThumb: String?,
    @SerializedName("idMeal")
    val mealId: String?
)
