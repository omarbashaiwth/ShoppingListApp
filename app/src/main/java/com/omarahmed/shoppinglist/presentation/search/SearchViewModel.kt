package com.omarahmed.shoppinglist.presentation.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(

):ViewModel() {

    private val _querySearchState = mutableStateOf("")
    val querySearchState: State<String> = _querySearchState

    fun setQuery(query: String ) {
        _querySearchState.value = query
    }

}