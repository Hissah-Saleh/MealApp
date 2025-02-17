package com.example.mealrespie.data.models

import com.google.gson.annotations.SerializedName

data class CategoryListDTO(
    @SerializedName("meals")
    val categories : List<CategoryDTO>?
)

data class CategoryDTO(
    @SerializedName("strCategory")
    val category : String?
)
