package com.omarahmed.shoppinglist.core.util.connectivity

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalContext
import com.omarahmed.shoppinglist.core.domain.states.ConnectionState
import com.omarahmed.shoppinglist.core.util.currentConnectivityState
import com.omarahmed.shoppinglist.core.util.observeConnectivityAsFlow
import kotlinx.coroutines.flow.collect

@Composable
fun connectivityState(): State<ConnectionState> {
    val context = LocalContext.current
    
    return produceState(initialValue = context.currentConnectivityState){
        context.observeConnectivityAsFlow().collect { value = it }
    }
}