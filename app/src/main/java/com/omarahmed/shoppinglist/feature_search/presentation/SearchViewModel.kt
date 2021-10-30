package com.omarahmed.shoppinglist.feature_search.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.omarahmed.shoppinglist.core.data.remote.ShoppingListApi
import com.omarahmed.shoppinglist.feature_list.presentation.screen_home.ShoppingListState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val api: ShoppingListApi
):ViewModel() {

    private val _querySearchState = mutableStateOf("")
    val querySearchState: State<String> = _querySearchState

    fun setQuery(query: String ) {
        _querySearchState.value = query
    }

    private val _state = mutableStateOf(ShoppingListState())
    val state: State<ShoppingListState> = _state


}