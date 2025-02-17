package com.example.mealrespie.domain.usecase

import com.example.mealrespie.data.models.MealDetailsDTO
import com.example.mealrespie.data.network.call_adapter.Response
import com.example.mealrespie.data.repository.Repository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetMealDetailsUseCase @Inject constructor(
    private val repository: Repository
) {

    suspend operator fun invoke(id: String): Response<MealDetailsDTO> {
        return repository.getMealDetails(id)

    }

}