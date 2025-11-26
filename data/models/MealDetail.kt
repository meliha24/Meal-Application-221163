package com.example.mealapp.data.models

data class MealDetail(
    val idMeal: String?,
    val strMeal: String?,
    val strCategory: String?,
    val strArea: String?,
    val strInstructions: String?,
    val strMealThumb: String?,
    val strYoutube: String?,
    // ingredients & measures up to 20 - include as nullable strings
    val strIngredient1: String?, val strIngredient2: String?, /* ... */ val strIngredient20: String?,
    val strMeasure1: String?, val strMeasure2: String?, /* ... */ val strMeasure20: String?
)