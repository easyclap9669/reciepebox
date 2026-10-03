package com.example.reciepebox.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.reciepebox.data.RecipeRepository
import com.example.reciepebox.data.RecipeStore
import com.example.reciepebox.ui.CreateEditRecipeScreen
import com.example.reciepebox.ui.FavouritesScreen
import com.example.reciepebox.ui.RecipeDetailsScreen
import com.example.reciepebox.ui.RecipeFeedScreen
import com.example.reciepebox.ui.ShoppingListScreen

object Routes {
    const val FEED = "feed"
    const val FAVOURITES = "favourites"
    const val SHOPPING = "shopping"
    const val CREATE = "create"
    const val EDIT = "edit/{id}"
    const val DETAILS = "details/{id}"

    fun edit(id: Int) = "edit/$id"
    fun details(id: Int) = "details/$id"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val store = remember { RecipeStore() }
    val repository = remember { RecipeRepository() }

    NavHost(
        navController = navController,
        startDestination = Routes.FEED
    ) {
        composable(Routes.FEED) {
            RecipeFeedScreen(
                store = store,
                repository = repository,
                onDetails = { navController.navigate(Routes.details(it)) },
                onCreate = { navController.navigate(Routes.CREATE) },
                onFavourites = { navController.navigate(Routes.FAVOURITES) },
                onShopping = { navController.navigate(Routes.SHOPPING) }
            )
        }

        composable(Routes.FAVOURITES) {
            FavouritesScreen(
                store = store,
                onBack = { navController.popBackStack() },
                onDetails = { navController.navigate(Routes.details(it)) }
            )
        }

        composable(Routes.SHOPPING) {
            ShoppingListScreen(
                store = store,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.CREATE) {
            CreateEditRecipeScreen(
                store = store,
                repository = repository,
                recipeId = null,
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Routes.EDIT,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { entry ->
            val id = entry.arguments?.getInt("id") ?: 0
            CreateEditRecipeScreen(
                store = store,
                repository = repository,
                recipeId = id,
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Routes.DETAILS,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { entry ->
            val id = entry.arguments?.getInt("id") ?: 0
            RecipeDetailsScreen(
                store = store,
                recipeId = id,
                onBack = { navController.popBackStack() },
                onEdit = { navController.navigate(Routes.edit(id)) }
            )
        }
    }
}
