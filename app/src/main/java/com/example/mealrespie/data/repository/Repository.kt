package com.example.mealrespie.data.repository

import com.example.mealrespie.data.api.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getMealList()= apiService.getMealList()
    suspend fun getMealByCategory(category: String)= apiService.getMealByCategory(category)
    suspend fun getMealDetails(id: String)= apiService.getMealDetails(id)
    suspend fun searchMeals(query: String)= apiService.searchMeals(query)

}