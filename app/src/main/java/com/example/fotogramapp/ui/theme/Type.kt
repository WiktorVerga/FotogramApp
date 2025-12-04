package com.example.fotogramapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.fotogramapp.R

// Font families (assicurati di avere i file nella cartella font)
val Inter = FontFamily(
    Font(R.font.inter_variable, FontWeight.Normal),
    Font(R.font.inter_variable, FontWeight.Medium),
    Font(R.font.inter_variable, FontWeight.Bold)
)

val Nunito = FontFamily(
    Font(R.font.nunito_variable, FontWeight.Normal),
    Font(R.font.nunito_variable, FontWeight.SemiBold),
    Font(R.font.nunito_variable, FontWeight.Bold)
)

val Typography = Typography(

    // Headings (Inter)
    headlineLarge = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 40.sp
    ),

    headlineMedium = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp
    ),

    headlineSmall = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.SemiBold,
        fontSize = 25.sp
    ),

    // Body (Nunito)
    bodyLarge = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.Normal,
        fontSize = 25.sp
    ),

    bodyMedium = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),

    labelLarge = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.Light,
        fontSize = 20.sp
    ),

    labelSmall = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.ExtraLight,
        fontSize = 15.sp
    )
)
