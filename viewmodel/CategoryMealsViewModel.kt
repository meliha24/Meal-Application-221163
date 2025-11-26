package com.example.mealapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealapp.data.models.MealSummary
import com.example.mealapp.data.repository.MealRepository
import kotlinx.coroutines.launch

class CategoryMealsViewModel(private val repo: MealRepository): ViewModel() {
    var meals by mutableStateOf<List<MealSummary>>(emptyList())
        private set
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
    var searchQuery by mutableStateOf("")

    fun loadMeals(category: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val res = repo.getMealsByCategory(category)
                meals = res.meals ?: emptyList()
            } catch (e: Exception) {
                errorMessage = e.message
            } finally { isLoading = false }
        }
    }

    suspend fun searchMeals(query: String) {
        // The API search returns full MealDetail objects; we map to summary
        try {
            val res = repo.searchMeals(query)
            meals = res.meals?.map {
                MealSummary(
                    it.strMeal ?: "",
                    it.strMealThumb ?: "",
                    it.idMeal ?: ""
                )
            } ?: emptyList()
        } catch (e: Exception) {
            // fallback: keep current meals or set errorMessage
            errorMessage = e.message
        }
    }

    val filteredMeals: List<MealSummary>
        get() = if (searchQuery.isBlank()) meals
        else meals.filter { it.strMeal.contains(searchQuery, ignoreCase = true) }
}
