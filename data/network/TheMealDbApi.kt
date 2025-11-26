package com.example.mealapp.data.network

import com.example.mealapp.data.models.CategoryResponse
import com.example.mealapp.data.models.MealDetailResponse
import com.example.mealapp.data.models.MealSummaryResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TheMealDbApi {
    @GET("api/json/v1/1/categories.php")
    suspend fun getCategories(): CategoryResponse

    @GET("api/json/v1/1/filter.php")
    suspend fun getMealsByCategory(@Query("c") category: String): MealSummaryResponse

    @GET("api/json/v1/1/lookup.php")
    suspend fun getMealById(@Query("i") id: String): MealDetailResponse

    @GET("api/json/v1/1/random.php")
    suspend fun getRandomMeal(): MealDetailResponse

    @GET("api/json/v1/1/search.php")
    suspend fun searchMeals(@Query("s") query: String): MealDetailResponse
}