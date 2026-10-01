package com.example.reciepebox.model

data class Ingredient(
    val name: String,
    val amount: Double,
    val unit: String
)

data class RecipeStep(
    val number: Int,
    val instruction: String
)

data class Recipe(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String,
    val servings: Int,
    val cookingTime: Int,
    val tags: List<String>,
    val ingredients: List<Ingredient>,
    val steps: List<RecipeStep>,
    val favorite: Boolean = false
)