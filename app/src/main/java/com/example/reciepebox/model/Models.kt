package com.example.reciepebox.model

data class Ingredient(
    val name: String = "",
    val amount: String = "",
    val unit: String = ""
)

data class Step(
    val number: Int = 0,
    val instruction: String = ""
)

data class Recipe(
    val id: Int = 0, val title: String = "", val description: String = "", val imageUrl: String? = null, val servings: Int = 1, val cookingTime: Int = 0, val tags: List<String> = emptyList(), val ingredients: List<Ingredient> = emptyList(), val steps: List<RecipeStep> = emptyList()
)

data class RecipesResponse(
    val success: Boolean = false,
    val count: Int = 0,
    val recipes: List<Recipe> = emptyList()
)

data class ShoppingItem(
    val id: Long,
    val name: String,
    val amount: String = "",
    val unit: String = "",
    val checked: Boolean = false
)
