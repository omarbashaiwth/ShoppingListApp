package com.omarahmed.shoppinglist.features.feature_icon_search.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omarahmed.shoppinglist.core.presentation.util.UiEvent
import com.omarahmed.shoppinglist.core.util.Resource
import com.omarahmed.shoppinglist.features.feature_icon_search.domain.repository.IconRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IconSearchViewModel @Inject constructor(
    private val iconRepository: IconRepository
) : ViewModel() {

    private val iconSearchState = mutableStateOf(IconSearchState())
    val iconSearch: State<IconSearchState> = iconSearchState

    private val eventFlow = MutableSharedFlow<UiEvent>()
    val events = eventFlow.asSharedFlow()


    fun onEvent(event: IconSearchEvent) {
        when(event){
            is IconSearchEvent.OnIconQueryChange -> {
                iconSearchState.value = iconSearchState.value.copy(iconSearchQuery = event.query)
            }
            is IconSearchEvent.OnIconSearch -> {
               viewModelScope.launch {
                   iconSearchState.value = iconSearchState.value.copy(isLoading = true)
                   when (val result = iconRepository.getIcons(event.query)) {
                       is Resource.Success -> {
                           val iconSearchResult = result.data
                           if (iconSearchResult != null) {
                               Log.d("IconSearchViewModel","size: " + iconSearchResult.totalCount.toString())
                               iconSearchState.value = iconSearchState.value.copy(
                                   isLoading = false,
                                   iconSearchResult = iconSearchResult
                               )
                           }

                       }
                       is Resource.Error -> {
                           Log.d("IconSearchViewModel",result.message ?: "error")
                           eventFlow.emit(UiEvent.ShowSnackbar(result.message ?: "Unknown error"))
                       }
                   }
               }
            }
        }
    }
}