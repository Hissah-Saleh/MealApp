package com.example.mealrespie.data.network.call_adapter

sealed class ApiResult<out T : Any?, out U : Any> {
    /**
     * Success response with body
     */
    data class Success<T : Any>(val body: T? = null) : ApiResult<T, Nothing>()

    /**
     * Loading response with body
     */
    object Loading: ApiResult<Nothing, Nothing>()

    /**
     * Failure response with body
     */
    data class ApiError<U : Any>(val body: String, val code: Int) : ApiResult<Nothing, U>()

    /**
     * Network error
     */
    data class NetworkError(val error: String?) : ApiResult<Nothing, Nothing>()
    object NoInternetConnection : ApiResult<Nothing, Nothing>()

    /**
     * For example, json parsing error
     */
    data class UnknownError(val error: String?) : ApiResult<Nothing, Nothing>()


}

typealias Response<T> = ApiResult<T, Any>