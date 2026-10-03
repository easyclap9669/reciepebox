
package com.example.reciepebox.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.reciepebox.data.RecipeStore
import com.example.reciepebox.model.Ingredient
import kotlin.math.max

// =============================================================
// RECIPE DETAILS SCREEN
// =============================================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailsScreen(
    store: RecipeStore,
    recipeId: Int,
    onBack: () -> Unit,
    onEdit: () -> Unit
) {

    // =========================================================
    // FIND RECIPE
    // =========================================================

    val recipe =
        store.recipes.firstOrNull {
            it.id == recipeId
        }

    // =========================================================
    // RECIPE NOT FOUND
    // =========================================================

    if (recipe == null) {

        Scaffold(

            topBar = {

                TopAppBar(

                    title = {
                        Text("Recipe")
                    },

                    navigationIcon = {

                        IconButton(
                            onClick = onBack
                        ) {

                            Icon(
                                imageVector =
                                    Icons.Default.ArrowBack,

                                contentDescription =
                                    "Back"
                            )
                        }
                    }
                )
            }

        ) { paddingValues ->

            Column(

                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(20.dp)
            ) {

                Text(

                    text =
                        "Recipe not found",

                    style =
                        MaterialTheme.typography
                            .titleLarge
                )
            }
        }

        return
    }

    // =========================================================
    // SERVINGS
    // =========================================================

    var selectedServings by remember(recipe.id) {

        mutableIntStateOf(
            max(
                1,
                recipe.servings
            )
        )
    }

    // =========================================================
    // MAIN SCREEN
    // =========================================================

    Scaffold(

        topBar = {

            TopAppBar(

                title = {

                    Text(
                        text =
                            recipe.title
                    )
                },

                navigationIcon = {

                    IconButton(
                        onClick = onBack
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.ArrowBack,

                            contentDescription =
                                "Back"
                        )
                    }
                },

                actions = {

                    // =================================================
                    // FAVOURITE
                    // =================================================

                    IconButton(

                        onClick = {

                            store.toggleFavourite(
                                recipe.id
                            )
                        }

                    ) {

                        Icon(

                            imageVector =
                                Icons.Default.Favorite,

                            contentDescription =
                                "Favourite"
                        )
                    }

                    // =================================================
                    // EDIT
                    // =================================================

                    IconButton(
                        onClick = onEdit
                    ) {

                        Icon(

                            imageVector =
                                Icons.Default.Edit,

                            contentDescription =
                                "Edit"
                        )
                    }
                }
            )
        }

    ) { paddingValues ->

        LazyColumn(

            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues),

            verticalArrangement =
                Arrangement.spacedBy(4.dp)
        ) {

            // =====================================================
            // HEADER
            // =====================================================

            item {

                // =================================================
                // RECIPE IMAGE
                // =================================================

                recipe.imageUrl
                    ?.takeIf {
                        it.isNotBlank()
                    }
                    ?.let { imageUrl ->

                        AsyncImage(

                            model =
                                imageUrl,

                            contentDescription =
                                recipe.title,

                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .height(250.dp),

                            contentScale =
                                ContentScale.Crop
                        )
                    }

                Column(

                    modifier =
                        Modifier.padding(16.dp)
                ) {

                    // =================================================
                    // TITLE
                    // =================================================

                    Text(

                        text =
                            recipe.title,

                        style =
                            MaterialTheme.typography
                                .headlineMedium
                    )

                    Spacer(
                        modifier =
                            Modifier.height(14.dp)
                    )

                    // =================================================
                    // DESCRIPTION
                    // =================================================

                    if (
                        recipe.description
                            .isNotBlank()
                    ) {

                        Text(

                            text =
                                "Description",

                            style =
                                MaterialTheme.typography
                                    .titleMedium
                        )

                        Spacer(
                            modifier =
                                Modifier.height(6.dp)
                        )

                        Text(

                            text =
                                recipe.description,

                            style =
                                MaterialTheme.typography
                                    .bodyLarge
                        )

                        Spacer(
                            modifier =
                                Modifier.height(12.dp)
                        )
                    }

                    // =================================================
                    // RECIPE INFO
                    // =================================================

                    Text(

                        text =
                            "${recipe.cookingTime} minutes • " +
                                    "Original: ${recipe.servings} servings",

                        style =
                            MaterialTheme.typography
                                .bodyMedium
                    )

                    // =================================================
                    // TAGS
                    // =================================================

                    if (
                        recipe.tags.isNotEmpty()
                    ) {

                        Text(

                            text =
                                recipe.tags
                                    .joinToString(" • "),

                            color =
                                MaterialTheme.colorScheme
                                    .primary,

                            modifier =
                                Modifier.padding(
                                    top = 6.dp
                                )
                        )
                    }

                    Spacer(
                        modifier =
                            Modifier.height(16.dp)
                    )

                    // =================================================
                    // SERVINGS
                    // =================================================

                    Text(

                        text =
                            "Servings",

                        style =
                            MaterialTheme.typography
                                .titleMedium
                    )

                    Row(

                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(
                                    vertical = 8.dp
                                ),

                        horizontalArrangement =
                            Arrangement.spacedBy(8.dp)
                    ) {

                        Button(

                            onClick = {

                                selectedServings =
                                    (
                                            selectedServings - 1
                                            ).coerceAtLeast(1)
                            }

                        ) {

                            Text("−")
                        }

                        Text(

                            text =
                                "$selectedServings servings",

                            modifier =
                                Modifier.padding(
                                    top = 12.dp
                                ),

                            style =
                                MaterialTheme.typography
                                    .bodyLarge
                        )

                        Button(

                            onClick = {

                                selectedServings =
                                    (
                                            selectedServings + 1
                                            ).coerceAtMost(100)
                            }

                        ) {

                            Text("+")
                        }
                    }

                    // =================================================
                    // SHOPPING LIST
                    // =================================================

                    Button(

                        onClick = {

                            store.addIngredients(
                                recipe
                            )
                        },

                        modifier =
                            Modifier.fillMaxWidth()
                    ) {

                        Icon(

                            imageVector =
                                Icons.Default.ShoppingCart,

                            contentDescription =
                                null
                        )

                        Text(
                            text =
                                "  Add all ingredients to shopping list"
                        )
                    }

                    Spacer(
                        modifier =
                            Modifier.height(16.dp)
                    )

                    // =================================================
                    // INGREDIENTS
                    // =================================================

                    Text(

                        text =
                            "Ingredients",

                        style =
                            MaterialTheme.typography
                                .headlineSmall
                    )
                }
            }

            // =====================================================
            // INGREDIENTS LIST
            // =====================================================

            items(

                items =
                    recipe.ingredients
            ) { ingredient ->

                IngredientScaledRow(

                    ingredient =
                        ingredient,

                    originalServings =
                        recipe.servings,

                    selectedServings =
                        selectedServings
                )
            }

            // =====================================================
            // COOKING STEPS HEADER
            // =====================================================

            item {

                Column(

                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 16.dp,
                                vertical = 20.dp
                            )
                ) {

                    Text(

                        text =
                            "Cooking Steps",

                        style =
                            MaterialTheme.typography
                                .headlineSmall
                    )

                    Spacer(
                        modifier =
                            Modifier.height(4.dp)
                    )

                    Text(

                        text =
                            if (
                                recipe.steps.isNotEmpty()
                            ) {

                                "${recipe.steps.size} steps"

                            } else {

                                "No cooking steps available"
                            },

                        style =
                            MaterialTheme.typography
                                .bodyMedium,

                        color =
                            MaterialTheme.colorScheme
                                .onSurfaceVariant
                    )
                }
            }

            // =====================================================
            // COOKING STEPS
            //
            // IMPORTANT:
            // JSON uses:
            //
            // "number": 1,
            // "description": "Cook the rice..."
            //
            // =====================================================

            if (
                recipe.steps.isNotEmpty()
            ) {

                items(

                    items =
                        recipe.steps.sortedBy {
                            it.number
                        }

                ) { step ->

                    StepCard(

                        number =
                            step.number,

                        // IMPORTANT:
                        // USE DESCRIPTION
                        description =
                            step.description
                    )
                }

            } else {

                item {

                    Card(

                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 16.dp
                                ),

                        colors =
                            CardDefaults.cardColors(
                                containerColor =
                                    MaterialTheme
                                        .colorScheme
                                        .surfaceVariant
                            )
                    ) {

                        Column(

                            modifier =
                                Modifier.padding(
                                    16.dp
                                )
                        ) {

                            Text(

                                text =
                                    "No cooking steps found.",

                                style =
                                    MaterialTheme
                                        .typography
                                        .bodyLarge
                            )

                            Spacer(
                                modifier =
                                    Modifier.height(4.dp)
                            )

                            Text(

                                text =
                                    "Add cooking steps to this recipe.",

                                style =
                                    MaterialTheme
                                        .typography
                                        .bodyMedium
                            )
                        }
                    }
                }
            }

            // =====================================================
            // BOTTOM SPACE
            // =====================================================

            item {

                Spacer(
                    modifier =
                        Modifier.height(32.dp)
                )
            }
        }
    }
}

// =============================================================
// INGREDIENT ROW
// =============================================================

@Composable
private fun IngredientScaledRow(
    ingredient: Ingredient,
    originalServings: Int,
    selectedServings: Int
) {

    val amount =
        scaleAmount(

            value =
                ingredient.amount,

            originalServings =
                originalServings,

            selectedServings =
                selectedServings
        )

    val displayText =
        listOf(

            amount,

            ingredient.unit,

            ingredient.name

        )
            .filter {
                it.isNotBlank()
            }
            .joinToString(" ")

    Text(

        text =
            "• $displayText",

        modifier =
            Modifier.padding(
                horizontal = 16.dp,
                vertical = 4.dp
            ),

        style =
            MaterialTheme.typography
                .bodyLarge
    )
}

// =============================================================
// SCALE INGREDIENT AMOUNT
// =============================================================

private fun scaleAmount(
    value: String,
    originalServings: Int,
    selectedServings: Int
): String {

    val number =
        value
            .trim()
            .toDoubleOrNull()
            ?: return value

    val original =
        originalServings
            .coerceAtLeast(1)

    val result =
        number *
                selectedServings.toDouble() /
                original.toDouble()

    return if (
        result % 1.0 == 0.0
    ) {

        result
            .toInt()
            .toString()

    } else {

        "%.2f"
            .format(result)
            .trimEnd('0')
            .trimEnd('.')
    }
}

// =============================================================
// STEP CARD
//
// IMPORTANT:
// This uses "description", matching the JSON.
// =============================================================

@Composable
private fun StepCard(
    number: Int,
    description: String
) {

    val cleanDescription =
        description.trim()

    Card(

        modifier =
            Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 5.dp
                ),

        colors =
            CardDefaults.cardColors(
                containerColor =
                    MaterialTheme.colorScheme
                        .surfaceVariant
            )
    ) {

        Column(

            modifier =
                Modifier.padding(16.dp)
        ) {

            // =================================================
            // STEP NUMBER
            // =================================================

            Text(

                text =
                    if (number > 0) {

                        "Step $number"

                    } else {

                        "Step"
                    },

                style =
                    MaterialTheme.typography
                        .titleMedium,

                color =
                    MaterialTheme.colorScheme
                        .primary
            )

            Spacer(
                modifier =
                    Modifier.height(6.dp)
            )

            // =================================================
            // STEP DESCRIPTION
            // =================================================

            Text(

                text =
                    if (
                        cleanDescription.isNotBlank()
                    ) {

                        cleanDescription

                    } else {

                        "No description provided."
                    },

                style =
                    MaterialTheme.typography
                        .bodyLarge
            )
        }
    }
}

