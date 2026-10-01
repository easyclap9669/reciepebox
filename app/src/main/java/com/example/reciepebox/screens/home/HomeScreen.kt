package com.example.reciepebox.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.reciepebox.components.RecipeBottomBar
import com.example.reciepebox.components.RecipeCard
import com.example.reciepebox.model.Recipe

@Composable
fun HomeScreen(
    recipes: List<Recipe>,
    onRecipeClick: (Int) -> Unit,
    onFavorite: (Int) -> Unit,
    onShoppingClick: () -> Unit,
    onFavoritesClick: () -> Unit,
    onCreateClick: () -> Unit
) {
    var search by remember { mutableStateOf("") }
    var selectedTag by remember { mutableStateOf("All") }

    val tags = listOf(
        "All",
        "Breakfast",
        "Lunch",
        "Dinner",
        "Healthy",
        "Pasta"
    )

    val filteredRecipes = recipes.filter { recipe ->

        val matchesSearch =
            recipe.title.contains(
                search,
                ignoreCase = true
            )

        val matchesTag =
            selectedTag == "All" ||
                    recipe.tags.contains(selectedTag)

        matchesSearch && matchesTag
    }

    Scaffold(

        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreateClick
            ) {
                Text("+")
            }
        },

        bottomBar = {
            RecipeBottomBar(
                selected = 0,
                onHome = {},
                onFavorites = onFavoritesClick,
                onShopping = onShoppingClick
            )
        }

    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {

            item {

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                Text(
                    text = "RecipeBox",
                    style = MaterialTheme.typography.headlineLarge
                )

                Text(
                    text = "Find something delicious",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                OutlinedTextField(
                    value = search,
                    onValueChange = {
                        search = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text("Search recipes...")
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Search"
                        )
                    },
                    singleLine = true
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                LazyRow(
                    horizontalArrangement =
                        Arrangement.spacedBy(8.dp)
                ) {

                    items(tags) { tag ->

                        FilterChip(
                            selected = selectedTag == tag,
                            onClick = {
                                selectedTag = tag
                            },
                            label = {
                                Text(tag)
                            }
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(10.dp)
                )
            }

            items(
                filteredRecipes,
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

            item {
                Spacer(
                    modifier = Modifier.height(100.dp)
                )
            }
        }
    }
}