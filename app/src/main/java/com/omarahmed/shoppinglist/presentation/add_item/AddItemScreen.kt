package com.omarahmed.shoppinglist.presentation.add_item

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun AddItemScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "Add Item Screen", color = Color.White)
    }
}