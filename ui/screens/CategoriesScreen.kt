package com.example.mealapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mealapp.ui.components.CategoryCard
import com.example.mealapp.viewmodel.CategoriesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
    viewModel: CategoriesViewModel,
    onCategoryClick: (String) -> Unit,
    onRandomClick: () -> Unit
) {
    val stateCategories = viewModel.filteredCategories
    Scaffold(topBar = {
        TopAppBar(title = { Text("Categories") }, actions = {
            IconButton(onClick = onRandomClick) {
                Icon(Icons.Default.Refresh, contentDescription = "Random recipe")
            }
        })
    }) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            OutlinedTextField(
                value = viewModel.searchQuery,
                onValueChange = { viewModel.searchQuery = it },
                label = { Text("Search categories") },
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
                LazyColumn {
                    items(stateCategories) { cat ->
                        CategoryCard(
                            category = cat,
                            onClick = { onCategoryClick(cat.strCategory) }
                        )
                    }
                }
            }
        }
    }
}
