package com.example.mealrespie.data.network.interceptor

import okhttp3.logging.HttpLoggingInterceptor.Logger
import timber.log.Timber
import javax.inject.Singleton

@Singleton
class HttpLogger: Logger  {

    override fun log(message: String) {
        Timber.tag("#OkHttp>>").d(message)
    }


}

