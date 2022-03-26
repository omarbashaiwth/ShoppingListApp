package com.omarahmed.shoppinglist.features.feature_search.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omarahmed.shoppinglist.core.domain.states.TextFieldState
import com.omarahmed.shoppinglist.core.presentation.util.UiEvent
import com.omarahmed.shoppinglist.core.util.Resource
import com.omarahmed.shoppinglist.features.feature_search.domain.repository.SearchRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepo: SearchRepo
):ViewModel() {

    private val _searchQuery = mutableStateOf(TextFieldState())
    val searchQuery: State<TextFieldState> = _searchQuery

    private val _state = mutableStateOf(SearchState())
    val state: State<SearchState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: SearchEvent){
        when(event){
            is SearchEvent.EnteredQuery -> {
                _searchQuery.value = _searchQuery.value.copy(
                    text = event.query
                )
            }
            is SearchEvent.Search -> {
                viewModelScope.launch {
                    _state.value = _state.value.copy(isLoading = true)
                   when(val result = searchRepo.getSearchResult(_searchQuery.value.text)){
                       is Resource.Success -> {
                           _state.value = _state.value.copy(
                               searchResult = result.data ?: emptyList(),
                               isLoading = false
                           )

                       }
                       is Resource.Error -> {
                           _eventFlow.emit(UiEvent.ShowSnackbar(result.message ?: "Unknown error occurred"))
                           _state.value = _state.value.copy(searchResult = emptyList(), isLoading = false)
                       }
                   }
                }

            }
        }
    }
}