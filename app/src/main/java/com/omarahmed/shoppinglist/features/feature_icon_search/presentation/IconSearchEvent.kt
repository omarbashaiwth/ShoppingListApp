package com.omarahmed.shoppinglist.features.feature_icon_search.presentation

sealed class IconSearchEvent {
    data class OnIconQueryChange(val query: String): IconSearchEvent()
    data class OnIconSearch(val query: String): IconSearchEvent()

}



