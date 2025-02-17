package com.example.mealrespie.ui.screens.main_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealrespie.data.models.CategoryDTO
import com.example.mealrespie.data.models.CategoryListDTO
import com.example.mealrespie.data.models.CategoryMealsDTO
import com.example.mealrespie.data.models.MealItem
import com.example.mealrespie.data.network.call_adapter.ApiResult
import com.example.mealrespie.domain.usecase.GetListOfMealsUseCase
import com.example.mealrespie.domain.usecase.GetMealByCategoryUseCase
import com.example.mealrespie.ui.ui_component.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getListOfMealsUseCase: GetListOfMealsUseCase,
    private val getMealByCategoryUseCase: GetMealByCategoryUseCase,
) : ViewModel() {

    private val _categories = MutableStateFlow<UIState<List<CategoryDTO>?>>(UIState.Loading)
    val categories: StateFlow<UIState<List<CategoryDTO>?>> get() = _categories
    private val _meals = MutableStateFlow<UIState<List<MealItem>?>?>(null)
    val meals: StateFlow<UIState<List<MealItem>?>?> get() = _meals

    private val _selectedIndex = MutableStateFlow<Int>(0)
    val selectedIndex: StateFlow<Int> get() = _selectedIndex

    init {
        getAllCategorizedMeals()
    }

    fun getAllCategorizedMeals() {
        viewModelScope.launch {
           when (val result = getListOfMealsUseCase()) {
                is ApiResult.ApiError ->  _categories.value = UIState.Error(msg = result.body)
                ApiResult.Loading -> _categories.value =  UIState.Loading
                is ApiResult.NetworkError ->  _categories.value = UIState.Error(msg = result.error.orEmpty())

                ApiResult.NoInternetConnection ->  _categories.value = UIState.NoInternetConnection

                is ApiResult.Success ->
                    if (!result.body?.categories.isNullOrEmpty()) {
                        _categories.value =  UIState.Success(result.body.categories)
                        getSelectedMealByCategory()
                    } else
                        _categories.value = UIState.Error(msg = "")

                is ApiResult.UnknownError ->
                    _categories.value = UIState.Error(msg = result.error.orEmpty())
            }
        }
    }


    fun getSelectedMealByCategory() {
        viewModelScope.launch {
            val result = _categories.value
            if (result is UIState.Success) {
                result.data?.getOrNull(_selectedIndex.value)?.let {
                    getMealByCategory(it.category)

                }

            }
        }
    }

    fun getMealByCategory(category: String?) {
        viewModelScope.launch {
            _meals.value= UIState.Loading
            _meals.value = when (val result = getMealByCategoryUseCase(category.orEmpty())) {
                is ApiResult.ApiError -> UIState.Error(msg = result.body)
                ApiResult.Loading -> UIState.Loading
                is ApiResult.NetworkError ->
                    UIState.Error(msg = result.error.orEmpty())

                ApiResult.NoInternetConnection ->
                    UIState.NoInternetConnection

                is ApiResult.Success ->
                    if (!result.body?.meals.isNullOrEmpty()) {
                        UIState.Success(result.body.meals)

                    } else
                        UIState.NoInternetConnection

                is ApiResult.UnknownError -> UIState.Error(msg = result.error.orEmpty())
            }
        }

    }

    fun updateIndex(index: Int) {
        _selectedIndex.value = index
    }
}