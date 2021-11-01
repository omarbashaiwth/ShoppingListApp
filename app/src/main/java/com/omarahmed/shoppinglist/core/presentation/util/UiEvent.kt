package com.omarahmed.shoppinglist.core.presentation.util

sealed class UiEvent{
    data class ShowSnackbar(val message: String): UiEvent()
}