package com.example.mealrespie.data.models

import com.google.gson.annotations.SerializedName


data class MealDetailsDTO(
    @SerializedName("meals") val meals: List<MealDetails>
)

data class MealDetails(
    @SerializedName("idMeal") val idMeal: String,
    @SerializedName("strMeal") val mealName: String,
    @SerializedName("strDrinkAlternate") val drinkAlternate: String?,
    @SerializedName("strCategory") val category: String?,
    @SerializedName("strArea") val area: String?,
    @SerializedName("strInstructions") val instructions: String?,
    @SerializedName("strMealThumb") val mealThumb: String?,
    @SerializedName("strTags") val tags: String?,
    @SerializedName("strYoutube") val youtubeUrl: String?,

    // Ingredients and measurements
    @SerializedName("strIngredient1") val ingredient1: String?,
    @SerializedName("strIngredient2") val ingredient2: String?,
    @SerializedName("strIngredient3") val ingredient3: String?,
    @SerializedName("strIngredient4") val ingredient4: String?,
    @SerializedName("strIngredient5") val ingredient5: String?,
    @SerializedName("strIngredient6") val ingredient6: String?,
    @SerializedName("strIngredient7") val ingredient7: String?,
    @SerializedName("strIngredient8") val ingredient8: String?,
    @SerializedName("strIngredient9") val ingredient9: String?,
    @SerializedName("strIngredient10") val ingredient10: String?,
    @SerializedName("strIngredient11") val ingredient11: String?,
    @SerializedName("strIngredient12") val ingredient12: String?,
    @SerializedName("strIngredient13") val ingredient13: String?,
    @SerializedName("strIngredient14") val ingredient14: String?,
    @SerializedName("strIngredient15") val ingredient15: String?,

    @SerializedName("strMeasure1") val measure1: String?,
    @SerializedName("strMeasure2") val measure2: String?,
    @SerializedName("strMeasure3") val measure3: String?,
    @SerializedName("strMeasure4") val measure4: String?,
    @SerializedName("strMeasure5") val measure5: String?,
    @SerializedName("strMeasure6") val measure6: String?,
    @SerializedName("strMeasure7") val measure7: String?,
    @SerializedName("strMeasure8") val measure8: String?,
    @SerializedName("strMeasure9") val measure9: String?,
    @SerializedName("strMeasure10") val measure10: String?,
    @SerializedName("strMeasure11") val measure11: String?,
    @SerializedName("strMeasure12") val measure12: String?,
    @SerializedName("strMeasure13") val measure13: String?,
    @SerializedName("strMeasure14") val measure14: String?,
    @SerializedName("strMeasure15") val measure15: String?,

    @SerializedName("strSource") val source: String?,
    @SerializedName("strImageSource") val imageSource: String?,
    @SerializedName("strCreativeCommonsConfirmed") val creativeCommonsConfirmed: String?,
    @SerializedName("dateModified") val dateModified: String?
)