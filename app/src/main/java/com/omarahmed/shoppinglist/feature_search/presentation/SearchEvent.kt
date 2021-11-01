package com.omarahmed.shoppinglist.feature_search.presentation

sealed class SearchEvent{
    data class EnteredQuery(val query: String): SearchEvent()
    data class Search(val query: String): SearchEvent()
}
