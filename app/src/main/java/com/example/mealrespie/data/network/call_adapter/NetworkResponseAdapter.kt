package com.example.mealrespie.data.network.call_adapter

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Converter
import java.lang.reflect.Type

/**
 * [responseType] Returns the value type that this adapter uses when converting the HTTP response
 * body to a Java object
 *
 * [adapt] Returns an instance of T which delegates to call, here we will use our NetworkResponseCall
 * that we just created.
 *
 * @param <S> Successful body type
 * @param <E> Error Body Type
 */

class NetworkResponseAdapter<S : Any, E : Any>(
    private val successType: Type,
    private val errorBodyConverter: Converter<ResponseBody, E>
) : CallAdapter<S, Call<ApiResult<S, E>>> {

    override fun responseType(): Type = successType

    override fun adapt(call: Call<S>): Call<ApiResult<S, E>> {
        return NetworkResponseCall(call, errorBodyConverter)
    }
}