package com.example.mealrespie.ui.ui_component

sealed class UIState<out T: Any?> {

    data class Success<T : Any?>( val data: T?= null) : UIState<T?>()
    data class Error( val code : Int?=null, val msg: String?= null) : UIState<Nothing>()
    data object Loading:  UIState<Nothing>()
    data object NoInternetConnection:  UIState<Nothing>()
    data object EmptyResult: UIState<Nothing>()
}