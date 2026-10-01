package com.example.reciepebox.screens.home.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.reciepebox.components.RecipeCard
import com.example.reciepebox.model.Recipe

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    recipes: List<Recipe>,
    onBack: () -> Unit,
    onRecipeClick: (Int) -> Unit,
    onFavorite: (Int) -> Unit
) {
    val favorites = recipes.filter {
        it.favorite
    }

    Scaffold(

        topBar = {
            TopAppBar(

                title = {
                    Text("Favorites")
                },

                navigationIcon = {
                    IconButton(
                        onClick = onBack
                    ) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }

    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement =
                Arrangement.spacedBy(4.dp)
        ) {

            items(
                favorites,
                key = { it.id }
            ) { recipe ->

                RecipeCard(
                    recipe = recipe,
                    onClick = {
                        onRecipeClick(recipe.id)
                    },
                    onFavorite = {
                        onFavorite(recipe.id)
                    }
                )
            }
        }
    }
}