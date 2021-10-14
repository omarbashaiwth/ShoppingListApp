package com.omarahmed.shoppinglist.presentation.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.omarahmed.shoppinglist.R

val quicksand = FontFamily(
    Font(R.font.quicksand_light,FontWeight.Light),
    Font(R.font.quicksand_reqular,FontWeight.Normal),
    Font(R.font.quicksand_medium,FontWeight.Medium),
    Font(R.font.quicksand_semibold,FontWeight.SemiBold),
    Font(R.font.quicksand_bold,FontWeight.Bold)
)
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = quicksand,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    h1 = TextStyle(
        fontFamily = quicksand,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        color = White
    )
)