package com.example.reciepebox.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.reciepebox.model.Ingredient

@Composable
fun IngredientRow(
    ingredient: Ingredient,
    scale: Double = 1.0
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp)
    ) {

        Text(
            text = ingredient.name,
            modifier = Modifier.weight(1f)
        )

        val amount = ingredient.amount * scale

        Text(
            text = "${formatAmount(amount)} ${ingredient.unit}"
        )
    }
}

private fun formatAmount(value: Double): String {
    return if (value % 1.0 == 0.0) {
        value.toInt().toString()
    } else {
        "%.1f".format(value)
    }
}