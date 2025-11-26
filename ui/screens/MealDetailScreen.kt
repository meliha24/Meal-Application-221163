package com.example.mealapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mealapp.ui.components.IngredientRow
import com.example.mealapp.util.IngredientParser
import com.example.mealapp.viewmodel.MealDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealDetailScreen(
    mealId: String?,
    viewModel: MealDetailViewModel,
    onBack: () -> Unit,
    openYoutube: (String) -> Unit
) {
    LaunchedEffect(mealId) {
        mealId?.let { viewModel.loadMealById(it) }
    }

    val meal = viewModel.meal
    Scaffold(topBar = {
        TopAppBar(title = { Text(meal?.strMeal ?: "Recipe") }, navigationIcon = {
            IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, contentDescription = "Back") }
        })
    }) { padding ->
        if (viewModel.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
        } else if (viewModel.errorMessage != null) {
            Text("Error: ${viewModel.errorMessage}", color = Color.Red, modifier = Modifier.padding(16.dp))
        } else if (meal == null) {
            Text("No recipe found", modifier = Modifier.padding(16.dp))
        } else {
            Column(Modifier.verticalScroll(rememberScrollState()).padding(16.dp)) {
                AsyncImage(
                    model = meal.strMealThumb,
                    contentDescription = meal.strMeal,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(Modifier.height(8.dp))
                Text(meal.strMeal ?: "", style = MaterialTheme.typography.titleLarge)

                Spacer(Modifier.height(8.dp))
                Text("Ingredients", style = MaterialTheme.typography.titleMedium)
                val ingredients = IngredientParser.parse(meal)
                ingredients.forEach { (ingredient, measure) ->
                    IngredientRow(ingredient = ingredient, measure = measure)
                }

                Spacer(Modifier.height(8.dp))
                Text("Instructions", style = MaterialTheme.typography.titleMedium)
                Text(meal.strInstructions ?: "", style = MaterialTheme.typography.bodyMedium)

                Spacer(Modifier.height(12.dp))
                meal.strYoutube?.takeIf { it.isNotEmpty() }?.let { yt ->
                    Button(onClick = { openYoutube(yt) }) {
                        Text("Open YouTube")
                    }
                }
            }
        }
    }
}
