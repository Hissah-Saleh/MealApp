package com.example.mealrespie.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.mealrespie.data.api.ApiService
import com.example.mealrespie.data.api.Endpoint
import com.example.mealrespie.data.network.call_adapter.NetworkResponseAdapterFactory
import com.example.mealrespie.data.network.interceptor.CacheInterceptor
import com.example.mealrespie.data.network.interceptor.HttpLogger
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // Make it globally available in the app
object AppModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(httpLogger: HttpLogger) =
        HttpLoggingInterceptor(httpLogger).setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun provideOkHttpClient(
        interceptor: HttpLoggingInterceptor,
        cache: Cache,
        cacheInterceptor: CacheInterceptor,
        chuckerInterceptor: ChuckerInterceptor
    ) = OkHttpClient
        .Builder()
        .callTimeout(30L, TimeUnit.SECONDS)
        .connectTimeout(30L, TimeUnit.SECONDS)
        .readTimeout(30L, TimeUnit.SECONDS)
        .writeTimeout(30L, TimeUnit.SECONDS)
        .addNetworkInterceptor(cacheInterceptor) // Add the
        .addInterceptor(interceptor) //or add any other interceptor
        .addInterceptor(chuckerInterceptor) //or add any other interceptor
        .cache(cache)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        apiResponseCallAdapter: NetworkResponseAdapterFactory,
        converterFactory: GsonConverterFactory,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Endpoint.BASE)
            .client(okHttpClient)  // Use OkHttp client for logging
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(apiResponseCallAdapter)
            .build()
    }

    @Provides
    @Singleton
    @JvmStatic
    fun provideChuckerInterceptor(
        @ApplicationContext context: Context,
        chuckerCollector: ChuckerCollector
    ): ChuckerInterceptor =
        ChuckerInterceptor.Builder(context)
            .collector(chuckerCollector)
            .alwaysReadResponseBody(true)
            .build()


    @Provides
    @Singleton
    @JvmStatic
    fun provideChuckerCollector(@ApplicationContext context: Context): ChuckerCollector =
        ChuckerCollector(context = context, showNotification = true,)

    @Provides
    @Singleton
    @JvmStatic
    fun provideCacheInterceptor(): CacheInterceptor = CacheInterceptor()


    @Provides
    @Singleton
    @JvmStatic
    fun provideHttpLogger(): HttpLogger = HttpLogger()

    @Provides
    @Singleton
    @JvmStatic
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    @JvmStatic
    fun provideCache(@ApplicationContext context: Context): Cache =
        Cache(context.cacheDir, 10L * 1024L * 1024L)


    @Provides
    @Singleton
    @JvmStatic
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)

    @Provides
    @Singleton
    internal fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}