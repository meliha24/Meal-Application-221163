package com.example.mealapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mealapp.data.network.RetrofitProvider
import com.example.mealapp.data.repository.MealRepository
import com.example.mealapp.ui.screens.CategoriesScreen
import com.example.mealapp.ui.screens.CategoryMealsScreen
import com.example.mealapp.ui.screens.MealDetailScreen
import com.example.mealapp.viewmodel.CategoriesViewModel
import com.example.mealapp.viewmodel.CategoryMealsViewModel
import com.example.mealapp.viewmodel.MealDetailViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val api = RetrofitProvider.provideApi()
        val repo = MealRepository(api)

        setContent {
            AppNavigation(repo)
        }
    }
}

@Composable
fun AppNavigation(repo: MealRepository) {
    val navController = rememberNavController()
    val context = LocalContext.current

    // Generic function to create ViewModels
    fun <T : ViewModel> viewModelFactory(factory: () -> T): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <R : ViewModel> create(modelClass: Class<R>): R {
                return factory() as R
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = "categories"
    ) {

        // -----------------------------
        // CATEGORIES SCREEN
        // -----------------------------
        composable("categories") {

            val vm: CategoriesViewModel =
                androidx.lifecycle.viewmodel.compose.viewModel(
                    factory = viewModelFactory { CategoriesViewModel(repo) }
                )

            CategoriesScreen(
                viewModel = vm,
                onCategoryClick = { cat ->
                    navController.navigate("categoryMeals/${Uri.encode(cat)}")
                },
                onRandomClick = {
                    vm.viewModelScope.launch {
                        val res = repo.getRandomMeal()
                        val id = res.meals?.firstOrNull()?.idMeal
                        id?.let { navController.navigate("mealDetail/$it") }
                    }
                }
            )
        }

        // -----------------------------
        // CATEGORY MEALS SCREEN
        // -----------------------------
        composable(
            route = "categoryMeals/{category}",
            arguments = listOf(navArgument("category") { type = NavType.StringType })
        ) { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category") ?: ""

            val vm: CategoryMealsViewModel =
                androidx.lifecycle.viewmodel.compose.viewModel(
                    factory = viewModelFactory { CategoryMealsViewModel(repo) }
                )

            CategoryMealsScreen(
                category = category,
                viewModel = vm,
                onMealClick = { id ->
                    navController.navigate("mealDetail/$id")
                }
            )
        }

        // -----------------------------
        // MEAL DETAIL SCREEN
        // -----------------------------
        composable(
            route = "mealDetail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""

            val vm: MealDetailViewModel =
                androidx.lifecycle.viewmodel.compose.viewModel(
                    factory = viewModelFactory { MealDetailViewModel(repo) }
                )

            MealDetailScreen(
                mealId = id,
                viewModel = vm,
                onBack = { navController.popBackStack() },
                openYoutube = { url ->
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                }
            )
        }
    }
}
