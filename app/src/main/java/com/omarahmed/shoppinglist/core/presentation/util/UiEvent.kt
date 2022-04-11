package com.omarahmed.shoppinglist.core.presentation.util

import com.ramcosta.composedestinations.spec.Direction

sealed class UiEvent{
    data class ShowSnackbar(val message: String): UiEvent()
    data class Navigate(val destination: Direction): UiEvent()
}