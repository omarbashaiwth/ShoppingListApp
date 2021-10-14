package com.omarahmed.shoppinglist.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun TestScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "TestScreen", color = Color.White)
    }
}