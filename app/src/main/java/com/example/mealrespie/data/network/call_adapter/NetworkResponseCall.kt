package com.example.mealrespie.data.network.call_adapter
import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response
import java.net.UnknownHostException

internal class NetworkResponseCall<S : Any, E : Any>(
    private val delegate: Call<S>,
    private val errorConverter: Converter<ResponseBody, E>
) : Call<ApiResult<S, E>> {

    /**
     * What is the [enqueue] method?
     *
     * Asynchronously send the request and notify callback of its response or if an error occurred
     * talking to the server, creating the request, or processing the response.
     */

    override fun enqueue(callback: Callback<ApiResult<S, E>>) {

        /**
         * enqueue takes a callback which has two methods to implement:
         *
         * **onResponse**: which is invoked for a received HTTP response, this response could be success
         * response or failure one. So we have to check here if the response is successful, we return
         * the success state of our NetworkResponse sealed class If it’s not a success response, we try
         * to parse the error body as the expected error data class we provide as a type, if the parse
         * succeeded we return the error as ApiError state, otherwise it’s UnknownError.
         *
         * **onFailure**: which is invoked when a network exception occurred talking to the server or when
         * an unexpected exception occurred creating the request or processing the response. Here we can
         * simply check if the exception is IOException then we return the NetworkError state, otherwise
         * it should be UnknownError state.
         *
         */

        return delegate.enqueue(object : Callback<S> {
            override fun onResponse(call: Call<S>, response: Response<S>) {
                val header = response.headers()

                val body = response.body()
                val code = response.code()
                val error = response.errorBody()

                if (response.isSuccessful) {
                    if (body != null) {
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(
                                ApiResult.Success(
                                    body
                                )
                            )
                        )
                    } else {
                        // Response is successful but the body is null
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(ApiResult.UnknownError(null))
                        )
                    }
                } else {
                    val errorBody = when {
                        error == null -> null
                        error.contentLength() == 0L -> null
                        else -> try {
                            errorConverter.convert(error)
                        } catch (ex: Exception) {
                            null
                        }
                    }
                    if (errorBody != null) {
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(ApiResult.ApiError(errorBody.toString(), code))
                        )
                    } else {
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(ApiResult.UnknownError(null))
                        )
                    }
                }
            }

            override fun onFailure(call: Call<S>, throwable: Throwable) {
                val networkResponse = when (throwable) {
                                        is UnknownHostException -> {
                        // No internet connection error
                        ApiResult.NoInternetConnection
                    }

                    else -> {
                        // For other types of errors
                        ApiResult.NetworkError(throwable.localizedMessage ?: "Unknown error")
                    }
                }
                callback.onResponse(this@NetworkResponseCall, Response.success(networkResponse))
            }
        })
    }

    override fun isExecuted() = delegate.isExecuted

    override fun clone() = NetworkResponseCall(delegate.clone(), errorConverter)

    override fun isCanceled() = delegate.isCanceled

    override fun cancel() = delegate.cancel()

    override fun execute(): Response<ApiResult<S, E>> {
        //because it will send a request Synchronously
        throw UnsupportedOperationException("NetworkResponseCall doesn't support execute")
    }

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}