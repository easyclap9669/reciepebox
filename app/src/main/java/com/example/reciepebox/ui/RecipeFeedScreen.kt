package com.example.reciepebox.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.reciepebox.data.RecipeRepository
import com.example.reciepebox.data.RecipeStore
import com.example.reciepebox.model.Recipe
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeFeedScreen(
    store: RecipeStore,
    repository: RecipeRepository,
    onDetails: (Int) -> Unit,
    onCreate: () -> Unit,
    onFavourites: () -> Unit,
    onShopping: () -> Unit
) {
    var search by remember { mutableStateOf("") }
    var selectedTag by remember { mutableStateOf<String?>(null) }
    var loading by remember { mutableStateOf(store.recipes.isEmpty()) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        if (store.recipes.isEmpty()) {
            loading = true
            repository.getRecipes().onSuccess {
                store.setRecipes(it)
                error = null
            }.onFailure {
                error = "Could not connect to server: ${it.message ?: "Unknown error"}"
            }
            loading = false
        }
    }

    val tags = store.recipes.flatMap { it.tags }.distinct().sorted()

    val filtered = store.recipes.filter { recipe ->
        val textMatch =
            search.isBlank() ||
                recipe.title.contains(search, true) ||
                recipe.description.contains(search, true) ||
                recipe.tags.any { it.contains(search, true) }

        val tagMatch = selectedTag == null || recipe.tags.contains(selectedTag)

        textMatch && tagMatch
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("RecipeBox") },
                actions = {
                    IconButton(onClick = onFavourites) {
                        Icon(Icons.Default.Favorite, "Favourites")
                    }
                    IconButton(onClick = onShopping) {
                        Icon(Icons.Default.ShoppingCart, "Shopping list")
                    }
                    IconButton(onClick = onCreate) {
                        Icon(Icons.Default.Add, "Create recipe")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            OutlinedTextField(
                value = search,
                onValueChange = { search = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                leadingIcon = {
                    Icon(Icons.Default.Search, "Search")
                },
                label = { Text("Search recipes") }
            )

            if (tags.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = selectedTag == null,
                        onClick = { selectedTag = null },
                        label = { Text("All") }
                    )
                    tags.take(5).forEach { tag ->
                        FilterChip(
                            selected = selectedTag == tag,
                            onClick = { selectedTag = if (selectedTag == tag) null else tag },
                            label = { Text(tag) }
                        )
                    }
                }
            }

            when {
                loading -> {
                    Box(Modifier.fillMaxSize()) {
                        CircularProgressIndicator()
                    }
                }

                error != null && store.recipes.isEmpty() -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp)
                    ) {
                        Text(error ?: "", color = MaterialTheme.colorScheme.error)
                        Button(onClick = {
                            loading = true
                            error = null
                        }) {
                            Text("Retry")
                        }
                    }
                }

                filtered.isEmpty() -> {
                    Box(Modifier.fillMaxSize()) {
                        Text("No recipes found.", modifier = Modifier.padding(20.dp))
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(filtered, key = { it.id }) { recipe ->
                            FeedRecipeCard(
                                recipe = recipe,
                                favourite = store.isFavourite(recipe.id),
                                onFavourite = { store.toggleFavourite(recipe.id) },
                                onClick = { onDetails(recipe.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FeedRecipeCard(
    recipe: Recipe,
    favourite: Boolean,
    onFavourite: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors()
    ) {
        Column {
            recipe.imageUrl?.takeIf { it.isNotBlank() }?.let {
                AsyncImage(
                    model = it,
                    contentDescription = recipe.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(220.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp)
            ) {
                Column(Modifier.weight(1f)) {
                    Text(recipe.title, style = MaterialTheme.typography.titleLarge)
                    Text(
                        "${recipe.cookingTime} min • ${recipe.servings} servings",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    if (recipe.tags.isNotEmpty()) {
                        Text(
                            recipe.tags.joinToString(" • "),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                IconButton(onClick = onFavourite) {
                    Icon(
                        Icons.Default.Favorite,
                        contentDescription = "Favourite",
                        tint = if (favourite)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
