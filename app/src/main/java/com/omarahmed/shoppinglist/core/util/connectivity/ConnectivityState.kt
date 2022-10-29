package com.omarahmed.shoppinglist.core.util.connectivity

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.omarahmed.shoppinglist.core.domain.states.ConnectionState
import kotlinx.coroutines.flow.collect

@Composable
fun connectivityState(lifecycle: Lifecycle): State<ConnectionState> {
    val context = LocalContext.current
    
    return produceState(initialValue = context.currentConnectivityState){
        context.observeConnectivityAsFlow(lifecycle).collect { value = it }
    }
}