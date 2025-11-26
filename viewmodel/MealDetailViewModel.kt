package com.example.mealapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealapp.data.models.MealDetail
import com.example.mealapp.data.repository.MealRepository
import kotlinx.coroutines.launch

class MealDetailViewModel(private val repo: MealRepository): ViewModel() {
    var meal by mutableStateOf<MealDetail?>(null)
        private set
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    fun loadMealById(id: String) {
        viewModelScope.launch {
            isLoading = true; errorMessage = null
            try {
                val res = repo.getMealById(id)
                meal = res.meals?.firstOrNull()
            } catch (e: Exception) {
                errorMessage = e.message
            } finally { isLoading = false }
        }
    }

    fun loadRandomMeal() {
        viewModelScope.launch {
            isLoading = true; errorMessage = null
            try {
                val res = repo.getRandomMeal()
                meal = res.meals?.firstOrNull()
            } catch (e: Exception) {
                errorMessage = e.message
            } finally { isLoading = false }
        }
    }
}
