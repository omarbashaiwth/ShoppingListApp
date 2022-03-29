package com.omarahmed.shoppinglist.features.feature_auth.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omarahmed.shoppinglist.core.data.DataStoreManager
import com.omarahmed.shoppinglist.core.presentation.util.UiEvent
import com.omarahmed.shoppinglist.core.util.Resource
import com.omarahmed.shoppinglist.features.destinations.HomeScreenDestination
import com.omarahmed.shoppinglist.features.destinations.LoginScreenDestination
import com.omarahmed.shoppinglist.features.feature_auth.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    dataStoreManager: DataStoreManager
): ViewModel() {

    private val eventChannel = Channel<UiEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            val token = dataStoreManager.getToken.first()
            when(authRepository.authenticate("Bearer $token")){
                is Resource.Success -> {
                    eventChannel.send(UiEvent.Navigate(HomeScreenDestination))
                }
                is Resource.Error -> {
                    eventChannel.send(UiEvent.Navigate(LoginScreenDestination()))

                }
            }
        }
    }

}