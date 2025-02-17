package com.example.mealrespie.ui.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealrespie.data.models.MealDetails
import com.example.mealrespie.data.models.MealDetailsDTO
import com.example.mealrespie.data.network.call_adapter.ApiResult
import com.example.mealrespie.domain.usecase.GetMealDetailsUseCase
import com.example.mealrespie.ui.ui_component.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getMealDetailsUseCase: GetMealDetailsUseCase,
) : ViewModel() {


    private val _mealDetailsState = MutableStateFlow<UIState<MealDetails?>>(UIState.Loading)
    val mealDetailsState: StateFlow<UIState<MealDetails?>> get() = _mealDetailsState

    fun getMeal(id: String) {
        viewModelScope.launch {
            when (val result = getMealDetailsUseCase(id)) {
                is ApiResult.ApiError -> _mealDetailsState.value = UIState.Error(msg = result.body)
                ApiResult.Loading -> _mealDetailsState.value = UIState.Loading
                is ApiResult.NetworkError -> _mealDetailsState.value =
                    UIState.Error(msg = result.error.orEmpty())

                ApiResult.NoInternetConnection -> _mealDetailsState.value =
                    UIState.NoInternetConnection

                is ApiResult.Success -> _mealDetailsState.value =
                    UIState.Success(result.body?.meals?.firstOrNull())

                is ApiResult.UnknownError -> _mealDetailsState.value =
                    UIState.Error(msg = result.error.orEmpty())
            }
        }
    }
}