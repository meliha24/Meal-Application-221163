package com.example.mealapp.data.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import okhttp3.logging.HttpLoggingInterceptor


object RetrofitProvider {
    private const val BASE_URL = "https://www.themealdb.com/"

    fun provideApi(): TheMealDbApi {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(TheMealDbApi::class.java)
    }
}