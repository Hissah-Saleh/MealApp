package com.example.mealrespie.domain.usecase

import com.example.mealrespie.data.models.CategoryListDTO
import com.example.mealrespie.data.network.call_adapter.Response
import com.example.mealrespie.data.repository.Repository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetListOfMealsUseCase @Inject constructor(
    private val repository: Repository
) {

    suspend operator fun invoke(): Response<CategoryListDTO> {
        return repository.getMealList()
    }
}