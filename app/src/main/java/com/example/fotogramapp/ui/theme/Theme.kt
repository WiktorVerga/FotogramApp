package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = Primary,
    onPrimary = White,

    secondary = Secondary,
    onSecondary = NeutralDark,

    tertiary = Tertiary,
    onTertiary = NeutralDark,

    background = NeutralLight,
    onBackground = NeutralDark,

    surface = White,
    onSurface = NeutralDark,

    primaryContainer = Accent,
    onPrimaryContainer = NeutralDark
)

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = AppTypography,
        content = content
    )
}
