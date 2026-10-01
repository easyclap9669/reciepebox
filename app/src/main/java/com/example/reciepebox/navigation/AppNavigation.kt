package com.example.reciepebox.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.reciepebox.data.sampleRecipes

import com.example.reciepebox.screens.home.HomeScreen
import com.example.reciepebox.screens.home.create.CreateRecipeScreen
import com.example.reciepebox.screens.home.detail.RecipeDetailScreen
import com.example.reciepebox.screens.home.favorites.FavoritesScreen
import com.example.reciepebox.screens.home.shopping.ShoppingListScreen


@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    var recipes by remember {
        mutableStateOf(sampleRecipes)
    }

    var shoppingItems by remember {
        mutableStateOf(emptyList<String>())
    }

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        // HOME
        composable("home") {

            HomeScreen(
                recipes = recipes,

                onRecipeClick = { id ->
                    navController.navigate("detail/$id")
                },

                onFavorite = { id ->

                    recipes = recipes.map { recipe ->

                        if (recipe.id == id) {
                            recipe.copy(
                                favorite = !recipe.favorite
                            )
                        } else {
                            recipe
                        }
                    }
                },

                onShoppingClick = {
                    navController.navigate("shopping")
                },

                onFavoritesClick = {
                    navController.navigate("favorites")
                },

                onCreateClick = {
                    navController.navigate("create")
                }
            )
        }

        // DETAIL
        composable(
            route = "detail/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                }
            )
        ) { entry ->

            val id =
                entry.arguments?.getInt("id") ?: 0

            val recipe =
                recipes.find {
                    it.id == id
                }

            if (recipe != null) {

                RecipeDetailScreen(

                    recipe = recipe,

                    onBack = {
                        navController.popBackStack()
                    },

                    onFavorite = {

                        recipes = recipes.map {
                            if (it.id == id) {
                                it.copy(
                                    favorite = !it.favorite
                                )
                            } else {
                                it
                            }
                        }
                    },

                    onAddShopping = {

                        val ingredients =
                            recipe.ingredients.map {
                                "${it.amount} ${it.unit} ${it.name}"
                            }

                        shoppingItems =
                            (shoppingItems + ingredients)
                                .distinct()
                    }
                )
            }
        }

        // FAVORITES
        composable("favorites") {

            FavoritesScreen(

                recipes = recipes,

                onBack = {
                    navController.popBackStack()
                },

                onRecipeClick = { id ->
                    navController.navigate("detail/$id")
                },

                onFavorite = { id ->

                    recipes = recipes.map {

                        if (it.id == id) {
                            it.copy(
                                favorite = !it.favorite
                            )
                        } else {
                            it
                        }
                    }
                }
            )
        }

        // SHOPPING
        composable("shopping") {

            ShoppingListScreen(

                items = shoppingItems,

                onBack = {
                    navController.popBackStack()
                },

                onRemove = { item ->

                    shoppingItems =
                        shoppingItems.filter {
                            it != item
                        }
                }
            )
        }

        // CREATE
        composable("create") {

            CreateRecipeScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}