
package com.example.reciepebox.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.reciepebox.data.RecipeRepository
import com.example.reciepebox.data.RecipeStore
import com.example.reciepebox.model.Ingredient
import com.example.reciepebox.model.Recipe
import com.example.reciepebox.model.RecipeStep
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEditRecipeScreen(
    store: RecipeStore,
    repository: RecipeRepository,
    recipeId: Int?,
    onBack: () -> Unit
) {

    // =========================================================
    // FIND EXISTING RECIPE
    // =========================================================

    val existing = recipeId?.let { id ->
        store.recipes.firstOrNull {
            it.id == id
        }
    }

    // =========================================================
    // FORM STATE
    // =========================================================

    var title by remember {
        mutableStateOf(
            existing?.title ?: ""
        )
    }

    var description by remember {
        mutableStateOf(
            existing?.description ?: ""
        )
    }

    var imageUrl by remember {
        mutableStateOf(
            existing?.imageUrl ?: ""
        )
    }

    var servings by remember {
        mutableStateOf(
            (existing?.servings ?: 2).toString()
        )
    }

    var cookingTime by remember {
        mutableStateOf(
            (existing?.cookingTime ?: 20).toString()
        )
    }

    var tags by remember {
        mutableStateOf(
            existing?.tags
                ?.joinToString(", ")
                ?: ""
        )
    }

    // =========================================================
    // INGREDIENT TEXT
    // =========================================================

    var ingredientsText by remember {

        mutableStateOf(

            existing?.ingredients
                ?.joinToString("\n") { ingredient ->

                    listOf(
                        ingredient.amount,
                        ingredient.unit,
                        ingredient.name
                    )
                        .filter {
                            it.isNotBlank()
                        }
                        .joinToString(" ")
                }
                ?: ""
        )
    }

    // =========================================================
    // STEP TEXT
    // =========================================================

    var stepsText by remember {

        mutableStateOf(

            existing?.steps
                ?.sortedBy {
                    it.number
                }
                ?.joinToString("\n") { step ->

                    step.description
                }
                ?: ""
        )
    }

    // =========================================================
    // SAVE STATE
    // =========================================================

    var saving by remember {
        mutableStateOf(false)
    }

    var message by remember {
        mutableStateOf("")
    }

    // =========================================================
    // SCREEN
    // =========================================================

    Scaffold(

        topBar = {

            TopAppBar(

                title = {

                    Text(
                        text =
                            if (recipeId == null) {
                                "Create Recipe"
                            } else {
                                "Edit Recipe"
                            }
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
                }
            )
        }

    ) { paddingValues ->

        Column(

            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(
                        rememberScrollState()
                    )
                    .padding(16.dp),

            verticalArrangement =
                Arrangement.spacedBy(10.dp)

        ) {

            // =================================================
            // TITLE
            // =================================================

            OutlinedTextField(

                value =
                    title,

                onValueChange = {
                    title = it
                },

                modifier =
                    Modifier.fillMaxWidth(),

                label = {
                    Text("Title")
                },

                singleLine = true
            )

            // =================================================
            // DESCRIPTION
            // =================================================

            OutlinedTextField(

                value =
                    description,

                onValueChange = {
                    description = it
                },

                modifier =
                    Modifier.fillMaxWidth(),

                minLines = 3,

                label = {
                    Text("Description")
                }
            )

            // =================================================
            // IMAGE URL
            // =================================================

            OutlinedTextField(

                value =
                    imageUrl,

                onValueChange = {
                    imageUrl = it
                },

                modifier =
                    Modifier.fillMaxWidth(),

                label = {
                    Text("Image URL")
                },

                singleLine = true
            )

            // =================================================
            // SERVINGS + COOKING TIME
            // =================================================

            Row(

                modifier =
                    Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.spacedBy(8.dp)

            ) {

                OutlinedTextField(

                    value =
                        servings,

                    onValueChange = {

                        servings =
                            it.filter(
                                Char::isDigit
                            )
                    },

                    modifier =
                        Modifier.weight(1f),

                    label = {
                        Text("Servings")
                    },

                    singleLine = true
                )

                OutlinedTextField(

                    value =
                        cookingTime,

                    onValueChange = {

                        cookingTime =
                            it.filter(
                                Char::isDigit
                            )
                    },

                    modifier =
                        Modifier.weight(1f),

                    label = {
                        Text("Minutes")
                    },

                    singleLine = true
                )
            }

            // =================================================
            // TAGS
            // =================================================

            OutlinedTextField(

                value =
                    tags,

                onValueChange = {
                    tags = it
                },

                modifier =
                    Modifier.fillMaxWidth(),

                label = {
                    Text(
                        "Tags (comma separated)"
                    )
                }
            )

            // =================================================
            // INGREDIENTS
            // =================================================

            Text(
                text = "Ingredients",
                style =
                    MaterialTheme.typography.titleLarge
            )

            Text(
                text =
                    "One ingredient per line. Example: 200 g chicken",

                style =
                    MaterialTheme.typography.bodySmall
            )

            OutlinedTextField(

                value =
                    ingredientsText,

                onValueChange = {
                    ingredientsText = it
                },

                modifier =
                    Modifier.fillMaxWidth(),

                minLines = 5,

                placeholder = {

                    Text(
                        "200 g chicken\n" +
                                "1 tbsp oil\n" +
                                "2 eggs"
                    )
                }
            )

            // =================================================
            // STEPS
            // =================================================

            Text(
                text = "Steps",
                style =
                    MaterialTheme.typography.titleLarge
            )

            Text(
                text =
                    "One cooking step per line.",

                style =
                    MaterialTheme.typography.bodySmall
            )

            OutlinedTextField(

                value =
                    stepsText,

                onValueChange = {
                    stepsText = it
                },

                modifier =
                    Modifier.fillMaxWidth(),

                minLines = 6,

                placeholder = {

                    Text(
                        "Cook the rice.\n" +
                                "Cut the chicken.\n" +
                                "Fry everything together."
                    )
                }
            )

            // =================================================
            // ERROR MESSAGE
            // =================================================

            if (message.isNotBlank()) {

                Text(

                    text =
                        message,

                    color =
                        MaterialTheme.colorScheme.error
                )
            }

            // =================================================
            // SAVE BUTTON
            // =================================================

            Button(

                onClick = {

                    // -----------------------------------------
                    // VALIDATE TITLE
                    // -----------------------------------------

                    if (
                        title
                            .trim()
                            .isBlank()
                    ) {

                        message =
                            "Please enter a recipe title."

                        return@Button
                    }

                    // -----------------------------------------
                    // CREATE RECIPE
                    // -----------------------------------------

                    val recipe =
                        Recipe(

                            id =
                                recipeId ?: 0,

                            title =
                                title.trim(),

                            description =
                                description.trim(),

                            imageUrl =
                                imageUrl
                                    .trim()
                                    .ifBlank {
                                        null
                                    },

                            servings =
                                servings
                                    .toIntOrNull()
                                    ?.coerceAtLeast(1)
                                    ?: 1,

                            cookingTime =
                                cookingTime
                                    .toIntOrNull()
                                    ?: 0,

                            tags =
                                tags
                                    .split(",")
                                    .map {
                                        it.trim()
                                    }
                                    .filter {
                                        it.isNotBlank()
                                    },

                            ingredients =
                                parseIngredients(
                                    ingredientsText
                                ),

                            // ---------------------------------
                            // IMPORTANT:
                            // Recipe expects List<RecipeStep>
                            // ---------------------------------

                            steps =
                                stepsText
                                    .lines()
                                    .mapIndexed { index, text ->

                                        RecipeStep(

                                            number =
                                                index + 1,

                                            description =
                                                text.trim()
                                        )
                                    }
                                    .filter {

                                        it.description
                                            .isNotBlank()
                                    }
                        )

                    // -----------------------------------------
                    // START SAVING
                    // -----------------------------------------

                    saving = true
                    message = ""

                    // -----------------------------------------
                    // SAVE LOCAL COPY
                    // -----------------------------------------

                    store.saveLocal(
                        recipe,
                        recipeId
                    )

                    // -----------------------------------------
                    // SAVE TO API
                    // -----------------------------------------

                    MainScope().launch {

                        try {

                            val result =

                                if (recipeId == null) {

                                    repository
                                        .createRecipe(
                                            recipe
                                        )

                                } else {

                                    repository
                                        .updateRecipe(
                                            recipe
                                        )
                                }

                            // API failure does not
                            // remove local recipe.

                            if (
                                result.isFailure
                            ) {

                                message =
                                    "Saved locally. API save failed."
                            }

                        } catch (
                            e: Exception
                        ) {

                            message =
                                "Saved locally. API connection failed."
                        }

                        saving = false

                        onBack()
                    }
                },

                modifier =
                    Modifier.fillMaxWidth(),

                enabled =
                    !saving

            ) {

                Text(

                    text =
                        if (saving) {
                            "Saving..."
                        } else {
                            "Save Recipe"
                        }
                )
            }
        }
    }
}

// =============================================================
// PARSE INGREDIENTS
// =============================================================

private fun parseIngredients(
    text: String
): List<Ingredient> {

    return text
        .lines()

        .map {
            it.trim()
        }

        .filter {
            it.isNotBlank()
        }

        .map { line ->

            val parts =
                line.split(
                    Regex("\\s+"),
                    limit = 3
                )

            when {

                // ---------------------------------------------
                // Example:
                // 200 g chicken
                // ---------------------------------------------

                parts.size >= 3 &&
                        parts[0]
                            .toDoubleOrNull() != null -> {

                    Ingredient(

                        name =
                            parts[2],

                        amount =
                            parts[0],

                        unit =
                            parts[1]
                    )
                }

                // ---------------------------------------------
                // Example:
                // 2 eggs
                // ---------------------------------------------

                parts.size >= 2 &&
                        parts[0]
                            .toDoubleOrNull() != null -> {

                    Ingredient(

                        name =
                            parts[1],

                        amount =
                            parts[0],

                        unit =
                            ""
                    )
                }

                // ---------------------------------------------
                // Example:
                // salt
                // ---------------------------------------------

                else -> {

                    Ingredient(

                        name =
                            line,

                        amount =
                            "",

                        unit =
                            ""
                    )
                }
            }
        }
}

