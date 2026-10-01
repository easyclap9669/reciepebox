package com.example.reciepebox.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val RecipeColorScheme = lightColorScheme(
    primary = Primary,
    secondary = PrimaryDark,
    background = Background,
    surface = Surface,
    onBackground = TextPrimary,
    onSurface = TextPrimary
)

@Composable
fun ReciepeboxTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = RecipeColorScheme,
        typography = AppTypography,
        content = content
    )
}