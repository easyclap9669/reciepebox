package com.example.reciepebox.screens.home.shopping



import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.reciepebox.components.ShoppingItemRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListScreen(
    items: List<String>,
    onBack: () -> Unit,
    onRemove: (String) -> Unit
) {
    val checkedItems = remember {
        mutableStateMapOf<String, Boolean>()
    }

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
                            Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }

    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {

            LazyColumn {

                items(
                    items.distinct()
                ) { item ->

                    ShoppingItemRow(
                        item = item,
                        checked =
                            checkedItems[item] == true,
                        onCheckedChange = {
                            checkedItems[item] = it
                        },
                        onRemove = {
                            onRemove(item)
                        }
                    )
                }
            }
        }
    }
}