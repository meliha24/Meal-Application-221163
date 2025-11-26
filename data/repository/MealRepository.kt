package com.example.mealapp.data.repository

import com.example.mealapp.data.network.TheMealDbApi

class MealRepository(private val api: TheMealDbApi) {

    suspend fun getCategories() = api.getCategories()
    suspend fun getMealsByCategory(category: String) = api.getMealsByCategory(category)
    suspend fun getMealById(id: String) = api.getMealById(id)
    suspend fun getRandomMeal() = api.getRandomMeal()
    suspend fun searchMeals(query: String) = api.searchMeals(query)
}
