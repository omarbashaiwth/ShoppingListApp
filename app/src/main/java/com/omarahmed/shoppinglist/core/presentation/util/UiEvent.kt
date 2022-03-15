package com.omarahmed.shoppinglist.core.presentation.util

import com.omarahmed.shoppinglist.destinations.DirectionDestination

sealed class UiEvent{
    data class ShowSnackbar(val message: String): UiEvent()
    data class Navigate(val destination: DirectionDestination? = null): UiEvent()
}