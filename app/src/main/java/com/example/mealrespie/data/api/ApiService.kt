package com.example.mealrespie.data.api

import com.example.mealrespie.data.models.CategoryListDTO
import com.example.mealrespie.data.models.CategoryMealsDTO
import com.example.mealrespie.data.models.MealDetailsDTO
import com.example.mealrespie.data.network.call_adapter.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(Endpoint.GET_CATEGORIES)
     suspend fun getMealList(@Query("c") list : String ="list" ): Response<CategoryListDTO>

    @GET(Endpoint.GET_CATEGORY_MEALS)
     suspend fun getMealByCategory(@Query("c") category : String ="" ): Response<CategoryMealsDTO>

    @GET(Endpoint.GET_MEAL_DETAILS)
     suspend fun getMealDetails(@Query("i") id : String ="" ): Response<MealDetailsDTO>

    @GET(Endpoint.SEARCH_MEALS)
     suspend fun searchMeals(@Query("s") query : String ="" ): Response<MealDetailsDTO>
}