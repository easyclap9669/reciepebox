package com.example.reciepebox.components



import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.reciepebox.model.RecipeStep

@Composable
fun StepItem(step: RecipeStep) {
    Row(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {

        Text(
            text = "${step.number}.",
            modifier = Modifier.padding(end = 10.dp)
        )

        Text(
            text = step.instruction
        )
    }
}