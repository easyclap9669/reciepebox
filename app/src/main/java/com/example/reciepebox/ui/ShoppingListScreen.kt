
package com.example.reciepebox.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.reciepebox.data.RecipeStore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListScreen(
    store: RecipeStore,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Shopping List")
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
                },

                actions = {
                    if (store.shoppingItems.any { it.checked }) {
                        Button(
                            onClick = {
                                store.clearCheckedShopping()
                            }
                        ) {
                            Text("Clear checked")
                        }
                    }
                }
            )
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),

            verticalArrangement =
                Arrangement.spacedBy(4.dp)
        ) {

            items(
                items = store.shoppingItems,
                key = { item -> item.id }
            ) { item ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),

                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    // Check/uncheck item
                    Checkbox(
                        checked = item.checked,

                        onCheckedChange = {
                            store.toggleShopping(item.id)
                        }
                    )

                    // Ingredient text
                    Text(
                        text = listOf(
                            item.amount,
                            item.unit,
                            item.name
                        )
                            .filter {
                                it.isNotBlank()
                            }
                            .joinToString(" "),

                        modifier = Modifier.weight(1f)
                    )

                    // Remove item
                    IconButton(
                        onClick = {
                            store.removeShopping(item.id)
                        }
                    ) {
                        Text("×")
                    }
                }
            }
        }
    }
}

