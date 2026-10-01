package com.example.reciepebox.data

import com.example.reciepebox.model.Ingredient
import com.example.reciepebox.model.Recipe
import com.example.reciepebox.model.RecipeStep

val sampleRecipes = listOf(

    Recipe(
        id = 1,
        title = "Chicken Fried Rice",
        description = "Easy and delicious homemade chicken fried rice.",
        imageUrl = "https://nourishingniki.com/wp-content/uploads/2024/08/shrimp-and-chicken-fried-rice-3-scaled.jpg",
        servings = 2,
        cookingTime = 25,
        tags = listOf("Lunch", "Dinner"),
        ingredients = listOf(
            Ingredient("Chicken breast", 200.0, "g"),
            Ingredient("Rice", 2.0, "cups"),
            Ingredient("Egg", 2.0, "pcs"),
            Ingredient("Soy sauce", 2.0, "tbsp"),
            Ingredient("Carrot", 1.0, "pcs"),
            Ingredient("Green onion", 2.0, "pcs")
        ),
        steps = listOf(
            RecipeStep(1, "Cook the rice and let it cool."),
            RecipeStep(2, "Cut the chicken into small pieces."),
            RecipeStep(3, "Cook the chicken in a hot pan."),
            RecipeStep(4, "Add carrots and green onions."),
            RecipeStep(5, "Add the rice and soy sauce."),
            RecipeStep(6, "Add eggs and stir everything together."),
            RecipeStep(7, "Cook for another 2–3 minutes and serve.")
        )
    ),

    Recipe(
        id = 2,
        title = "Creamy Pasta",
        description = "Creamy pasta that is quick and perfect for dinner.",
        imageUrl = "https://i0.wp.com/kennascooks.com/wp-content/uploads/2025/03/img_9240.jpg?w=1080&ssl=1",
        servings = 2,
        cookingTime = 20,
        tags = listOf("Dinner", "Pasta"),
        ingredients = listOf(
            Ingredient("Pasta", 250.0, "g"),
            Ingredient("Heavy cream", 200.0, "ml"),
            Ingredient("Parmesan", 50.0, "g"),
            Ingredient("Garlic", 2.0, "cloves"),
            Ingredient("Butter", 1.0, "tbsp"),
            Ingredient("Black pepper", 0.5, "tsp")
        ),
        steps = listOf(
            RecipeStep(1, "Boil the pasta until al dente."),
            RecipeStep(2, "Melt butter in a pan."),
            RecipeStep(3, "Add garlic and cook until fragrant."),
            RecipeStep(4, "Add heavy cream."),
            RecipeStep(5, "Add parmesan and black pepper."),
            RecipeStep(6, "Add cooked pasta and mix well."),
            RecipeStep(7, "Serve immediately.")
        )
    ),

    Recipe(
        id = 3,
        title = "Avocado Toast",
        description = "Simple healthy avocado toast for breakfast.",
        imageUrl = "https://www.eatingwell.com/thmb/PM3UlLhM0VbE6dcq9ZFwCnMyWHI=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/EatingWell-April-Avocado-Toast-Directions-04-5b5b86524a3d4b35ac4c57863f6095dc.jpg",
        servings = 1,
        cookingTime = 10,
        tags = listOf("Breakfast", "Healthy"),
        ingredients = listOf(
            Ingredient("Bread", 2.0, "slices"),
            Ingredient("Avocado", 1.0, "pcs"),
            Ingredient("Egg", 1.0, "pcs"),
            Ingredient("Salt", 0.25, "tsp"),
            Ingredient("Black pepper", 0.25, "tsp")
        ),
        steps = listOf(
            RecipeStep(1, "Toast the bread."),
            RecipeStep(2, "Mash the avocado."),
            RecipeStep(3, "Season the avocado with salt and pepper."),
            RecipeStep(4, "Spread avocado over the toast."),
            RecipeStep(5, "Add a cooked egg on top."),
            RecipeStep(6, "Serve immediately.")
        )
    )
)