package com.ssu.micropower.api

import com.ssu.micropower.app.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
    Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

fun provideOkHttpClient(
    authInterceptor: AuthInterceptor
) =
    OkHttpClient()
        .newBuilder()
        .addInterceptor(authInterceptor)
        .addInterceptor(
            HttpLoggingInterceptor()
                .apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
        )
        .build()

fun provideWeatherApi(retrofit: Retrofit): IWeatherService =
    retrofit.create(IWeatherService::class.java)

fun provideAuthApi(retrofit: Retrofit): IAuthService =
    retrofit.create(IAuthService::class.java)

fun provideLocationApi(retrofit: Retrofit): ILocationService =
    retrofit.create(ILocationService::class.java)
