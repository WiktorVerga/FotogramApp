package com.example.fotogramapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = NeutralDark,
    primaryContainer = PrimaryDesaturated,
    onPrimaryContainer = NeutralDark,

    secondary = Secondary,
    onSecondary = NeutralDark,
    secondaryContainer = SecondaryDesaturated,
    onSecondaryContainer = NeutralDark,

    tertiary = Accent,
    onTertiary = NeutralDark,
    tertiaryContainer = LightGray,
    onTertiaryContainer = NeutralDark,

    background = NeutralLight,
    onBackground = NeutralDark,

    surface = LightGray,
    onSurface = NeutralDark,
    surfaceContainer = White,

    error = Danger,
    onError = NeutralLight,
    errorContainer = DangerContainer,
    onErrorContainer = NeutralDark,

    outline = NeutralDark,
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    onPrimary = NeutralLight,
    primaryContainer = PrimaryDesaturatedDark,

    secondary = SecondaryDark,
    onSecondary = NeutralLight,
    secondaryContainer = SecondaryDesaturatedDark,

    tertiary = AccentDark,
    onTertiary = NeutralLight,
    tertiaryContainer = NeutralDark,
    onTertiaryContainer = LightGray,

    background = NeutralDark,
    onBackground = NeutralLight,

    surface = NeutralDark,
    onSurface = NeutralLight,
    surfaceContainer = Black,

    error = DangerDark,
    onError = NeutralLight,
    errorContainer = DangerContainerDark,
    onErrorContainer = NeutralDark,

    outline = LightGray,
)

@Composable
fun FotogramTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme =
        when {
            dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                val context = LocalContext.current
                if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            }

            darkTheme -> DarkColorScheme
            else -> LightColorScheme
        }

    val view = LocalView.current
    if (!view.isInEditMode) {
        val window = (view.context as Activity).window
        window.statusBarColor = colorScheme.background.toArgb()
        WindowCompat.getInsetsController(window, view)
            .isAppearanceLightStatusBars = !darkTheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
