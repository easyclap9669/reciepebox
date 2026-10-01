package com.example.reciepebox.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun RecipeBottomBar(
    selected: Int,
    onHome: () -> Unit,
    onFavorites: () -> Unit,
    onShopping: () -> Unit
) {
    NavigationBar {

        NavigationBarItem(
            selected = selected == 0,
            onClick = onHome,
            icon = {
                Icon(
                    Icons.Default.Home,
                    contentDescription = "Home"
                )
            },
            label = {
                Text("Home")
            }
        )

        NavigationBarItem(
            selected = selected == 1,
            onClick = onFavorites,
            icon = {
                Icon(
                    Icons.Default.Favorite,
                    contentDescription = "Favorites"
                )
            },
            label = {
                Text("Favorites")
            }
        )

        NavigationBarItem(
            selected = selected == 2,
            onClick = onShopping,
            icon = {
                Icon(
                    Icons.Default.ShoppingCart,
                    contentDescription = "Shopping"
                )
            },
            label = {
                Text("Shopping")
            }
        )
    }
}