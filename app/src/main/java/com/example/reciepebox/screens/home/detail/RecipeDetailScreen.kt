package com.example.reciepebox.screens.home.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.reciepebox.components.IngredientRow
import com.example.reciepebox.components.StepItem
import com.example.reciepebox.model.Recipe

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(
    recipe: Recipe,
    onBack: () -> Unit,
    onFavorite: () -> Unit,
    onAddShopping: () -> Unit
) {
    Scaffold(

        topBar = {
            TopAppBar(

                title = {
                    Text(recipe.title)
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
                },

                actions = {
                    IconButton(
                        onClick = onFavorite
                    ) {
                        Icon(
                            imageVector =
                                if (recipe.favorite)
                                    Icons.Default.Favorite
                                else
                                    Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite"
                        )
                    }
                }
            )
        }

    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {

            AsyncImage(
                model = recipe.imageUrl,
                contentDescription = recipe.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = recipe.title,
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = recipe.description
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                Text(
                    text = "${recipe.cookingTime} minutes • ${recipe.servings} servings"
                )

                Spacer(
                    modifier = Modifier.height(24.dp)
                )

                Text(
                    text = "Ingredients",
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                recipe.ingredients.forEach { ingredient ->

                    IngredientRow(
                        ingredient = ingredient
                    )
                }

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                Button(
                    onClick = onAddShopping,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add Ingredients to Shopping List")
                }

                Spacer(
                    modifier = Modifier.height(28.dp)
                )

                Text(
                    text = "Instructions",
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                recipe.steps.forEach { step ->

                    StepItem(
                        step = step
                    )
                }

                Spacer(
                    modifier = Modifier.height(40.dp)
                )
            }
        }
    }
}