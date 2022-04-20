package com.omarahmed.shoppinglist.core.domain.states

sealed class ConnectionState {
    object Available: ConnectionState()
    object Unavailable: ConnectionState()
}
