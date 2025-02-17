package com.example.meal.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealrespie.data.models.MealDetails
import com.example.mealrespie.data.models.MealDetailsDTO
import com.example.mealrespie.data.network.call_adapter.ApiResult
import com.example.mealrespie.domain.usecase.SearchMealsUseCase
import com.example.mealrespie.ui.ui_component.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMealsUseCase: SearchMealsUseCase
) : ViewModel() {


    private val _mealDetailsState = MutableStateFlow<UIState<List<MealDetails?>?>?>(null)
    val mealDetailsState: StateFlow<UIState<List<MealDetails?>?>?> get() = _mealDetailsState

    fun searchMeals(query: String){
        viewModelScope.launch {
            when (val result = searchMealsUseCase(query)) {
                is ApiResult.ApiError -> _mealDetailsState.value = UIState.Error(msg = result.body)
                ApiResult.Loading -> _mealDetailsState.value = UIState.Loading
                is ApiResult.NetworkError -> _mealDetailsState.value =
                    UIState.Error(msg = result.error.orEmpty())

                ApiResult.NoInternetConnection -> _mealDetailsState.value =
                    UIState.NoInternetConnection

                is ApiResult.Success ->  {
                    val data= result.body?.meals
                    _mealDetailsState.value = if(data?.isNotEmpty() == true)
                        UIState.Success(result.body.meals)
                    else
                        UIState.EmptyResult
                }

                is ApiResult.UnknownError -> _mealDetailsState.value =
                    UIState.Error(msg = result.error.orEmpty())
            }
        }
    }

}