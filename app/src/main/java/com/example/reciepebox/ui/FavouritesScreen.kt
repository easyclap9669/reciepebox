
package com.example.reciepebox.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.reciepebox.data.RecipeStore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouritesScreen(
    store: RecipeStore,
    onBack: () -> Unit,
    onDetails: (Int) -> Unit
) {
    val favouriteRecipes = store.recipes.filter {
        store.isFavourite(it.id)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Favourites")
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        if (favouriteRecipes.isEmpty()) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(20.dp)
            ) {
                Text("No favourite recipes yet.")
            }

        } else {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(
                    items = favouriteRecipes,
                    key = { recipe -> recipe.id }
                ) { recipe ->

                    Card(
                        onClick = {
                            onDetails(recipe.id)
                        },
                        modifier = Modifier.fillMaxSize()
                    ) {

                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {

                            Text(
                                text = recipe.title
                            )

                            Text(
                                text = "${recipe.cookingTime} min • ${recipe.servings} servings"
                            )
                        }
                    }
                }
            }
        }
    }
}
