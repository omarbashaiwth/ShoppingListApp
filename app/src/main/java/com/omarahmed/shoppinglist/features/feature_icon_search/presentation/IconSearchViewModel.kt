package com.omarahmed.shoppinglist.features.feature_icon_search.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omarahmed.shoppinglist.core.domain.states.TextFieldState
import com.omarahmed.shoppinglist.core.presentation.util.UiEvent
import com.omarahmed.shoppinglist.core.util.Resource
import com.omarahmed.shoppinglist.features.feature_icon_search.domain.repository.IconRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IconSearchViewModel @Inject constructor(
    private val iconRepository: IconRepository
) : ViewModel() {

    private val iconQueryState = mutableStateOf(TextFieldState())
    val iconQuery: State<TextFieldState> = iconQueryState

    private val searchResultState = mutableStateOf(IconSearchState())
    val searchResult: State<IconSearchState> = searchResultState

    private val eventChannel = Channel<UiEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onIconQueryChange(query: String) {
        iconQueryState.value = iconQuery.value.copy(text = query)
    }

    fun onSearch(query: String) = viewModelScope.launch {
        searchResultState.value = searchResultState.value.copy(isLoading = true)
        when (val result = iconRepository.getIcons(query)) {
            is Resource.Success -> {
                val iconSearchResult = result.data
                if (iconSearchResult != null) {
                    Log.d("IconSearchViewModel","size: " + iconSearchResult.totalCount.toString())
                    searchResultState.value = searchResultState.value.copy(
                        isLoading = false,
                        iconSearchResult = iconSearchResult
                    )
                }

            }
            is Resource.Error -> {
                Log.d("IconSearchViewModel",result.message ?: "error")
                eventChannel.send(UiEvent.ShowSnackbar(result.message ?: "Unknown error"))
            }
        }
    }
}