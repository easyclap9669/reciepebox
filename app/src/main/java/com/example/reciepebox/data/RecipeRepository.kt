package com.example.reciepebox.data

import com.example.reciepebox.api.RecipeApiClient
import com.example.reciepebox.model.Recipe

class RecipeRepository {

    suspend fun getRecipes(
        query: String? = null,
        tag: String? = null
    ): Result<List<Recipe>> {
        return runCatching {
            val response = RecipeApiClient.api.getRecipes(query, tag)
            if (response.success || response.recipes.isNotEmpty()) {
                response.recipes
            } else {
                emptyList()
            }
        }
    }

    suspend fun createRecipe(recipe: Recipe): Result<Recipe> =
        runCatching { RecipeApiClient.api.createRecipe(recipe) }

    suspend fun updateRecipe(recipe: Recipe): Result<Recipe> =
        runCatching { RecipeApiClient.api.updateRecipe(recipe.id, recipe) }

    suspend fun deleteRecipe(id: Int): Result<Unit> =
        runCatching { RecipeApiClient.api.deleteRecipe(id) }
}
