package com.example.mealapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealapp.data.models.Category
import com.example.mealapp.data.repository.MealRepository
import kotlinx.coroutines.launch

class CategoriesViewModel(private val repo: MealRepository): ViewModel() {
    var categories by mutableStateOf<List<Category>>(emptyList())
        private set
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    var searchQuery by mutableStateOf("")

    init { fetchCategories() }

    fun fetchCategories() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val res = repo.getCategories()
                categories = res.categories ?: emptyList()
            } catch (e: Exception) {
                errorMessage = e.message ?: "Unknown error"
            } finally { isLoading = false }
        }
    }

    val filteredCategories: List<Category>
        get() = if (searchQuery.isBlank()) categories
        else categories.filter { it.strCategory.contains(searchQuery, ignoreCase = true) }
}
