package com.omarahmed.shoppinglist.features.feature_search.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omarahmed.shoppinglist.core.presentation.util.UiEvent
import com.omarahmed.shoppinglist.core.util.Resource
import com.omarahmed.shoppinglist.features.feature_search.domain.repository.SearchRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepo: SearchRepo
):ViewModel() {


    private val searchItemState = mutableStateOf(SearchState())
    val searchItem: State<SearchState> = searchItemState

    private val eventFlow = MutableSharedFlow<UiEvent>()
    val events = eventFlow.asSharedFlow()

    private var searchJob: Job? = null

    fun onEvent(event: SearchEvent){
        when(event){
            is SearchEvent.EnteredQuery -> {
                searchItemState.value = searchItemState.value.copy(
                    searchQuery = event.query
                )
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    searchItems()
                }
            }
        }
    }

    private suspend fun searchItems(){
        searchItemState.value = searchItemState.value.copy(isLoading = true)
        when(val result = searchRepo.getSearchResult(searchItemState.value.searchQuery)){
            is Resource.Success -> {
                val data = result.data ?: emptyList()
                searchItemState.value = searchItemState.value.copy(
                    searchResult = data,
                    isLoading = false,
                )

            }
            is Resource.Error -> {
                eventFlow.emit(UiEvent.ShowSnackbar(result.message ?: "Unknown error occurred"))
                searchItemState.value = searchItemState.value.copy(searchResult = emptyList(), isLoading = false)
            }
        }
    }
}