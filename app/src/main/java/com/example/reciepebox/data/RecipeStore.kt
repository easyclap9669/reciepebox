package com.example.reciepebox.data

import androidx.compose.runtime.mutableStateListOf
import com.example.reciepebox.model.Ingredient
import com.example.reciepebox.model.Recipe
import com.example.reciepebox.model.ShoppingItem

class RecipeStore {

    val recipes = mutableStateListOf<Recipe>()
    val favourites = mutableStateListOf<Int>()
    val shoppingItems = mutableStateListOf<ShoppingItem>()

    private var nextLocalId = -1
    private var nextShoppingId = 1L

    fun setRecipes(items: List<Recipe>) {
        recipes.clear()
        recipes.addAll(items)
    }

    fun toggleFavourite(id: Int) {
        if (favourites.contains(id)) favourites.remove(id)
        else favourites.add(id)
    }

    fun isFavourite(id: Int): Boolean = favourites.contains(id)

    fun saveLocal(recipe: Recipe, existingId: Int? = null): Recipe {
        val id = existingId ?: nextLocalId--
        val saved = recipe.copy(id = id)

        val index = recipes.indexOfFirst { it.id == id }
        if (index >= 0) recipes[index] = saved
        else recipes.add(0, saved)

        return saved
    }

    fun deleteLocal(id: Int) {
        recipes.removeAll { it.id == id }
        favourites.remove(id)
    }

    fun addIngredients(recipe: Recipe) {
        recipe.ingredients.forEach { ingredient ->
            if (ingredient.name.isBlank()) return@forEach

            shoppingItems.add(
                ShoppingItem(
                    id = nextShoppingId++,
                    name = ingredient.name,
                    amount = ingredient.amount,
                    unit = ingredient.unit
                )
            )
        }
    }

    fun toggleShopping(id: Long) {
        val index = shoppingItems.indexOfFirst { it.id == id }
        if (index >= 0) {
            val old = shoppingItems[index]
            shoppingItems[index] = old.copy(checked = !old.checked)
        }
    }

    fun removeShopping(id: Long) {
        shoppingItems.removeAll { it.id == id }
    }

    fun clearCheckedShopping() {
        shoppingItems.removeAll { it.checked }
    }
}
