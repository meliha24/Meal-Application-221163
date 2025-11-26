package com.example.mealapp.util

import com.example.mealapp.data.models.MealDetail

object IngredientParser {
    fun parse(meal: MealDetail): List<Pair<String, String>> {
        val ingredients = mutableListOf<Pair<String, String>>()
        for (i in 1..20) {
            val ingField = MealDetail::class.members
                .firstOrNull { it.name == "strIngredient$i" }
                ?.call(meal) as? String?
            val measureField = MealDetail::class.members
                .firstOrNull { it.name == "strMeasure$i" }
                ?.call(meal) as? String?

            val ingredient = ingField?.trim()?.takeIf { it.isNotEmpty() && it != "null" }
            if (!ingredient.isNullOrBlank()) {
                val measure = measureField?.trim()?.takeIf { it.isNotEmpty() && it != "null" } ?: ""
                ingredients.add(Pair(ingredient, measure))
            }
        }
        return ingredients
    }
}
