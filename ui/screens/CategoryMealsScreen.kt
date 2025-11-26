package com.example.mealapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import com.example.mealapp.ui.components.MealCard
import com.example.mealapp.viewmodel.CategoryMealsViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryMealsScreen(
    category: String,
    viewModel: CategoryMealsViewModel,
    onMealClick: (String) -> Unit
) {
    LaunchedEffect(Unit) { viewModel.loadMeals(category) }

    Scaffold(topBar = { TopAppBar(title = { Text(category) }) }) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            OutlinedTextField(
                value = viewModel.searchQuery,
                onValueChange = {
                    viewModel.searchQuery = it
                    if (it.length >= 3) {
                        viewModel.viewModelScope.launch { viewModel.searchMeals(it) }
                    }
                },
                label = { Text("Search meals") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            if (viewModel.isLoading) {
                Box(
                    Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) { CircularProgressIndicator() }
            } else if (viewModel.errorMessage != null) {
                Text(
                    "Error: ${viewModel.errorMessage}",
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    content = {
                        items(viewModel.filteredMeals) { meal ->
                            MealCard(meal = meal, onClick = { onMealClick(meal.idMeal) })
                        }
                    }
                )
            }
        }
    }
}
