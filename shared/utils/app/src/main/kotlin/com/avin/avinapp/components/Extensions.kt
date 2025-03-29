package com.avin.avinapp.components

import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun <T : Any> Value<T>.toFlow(): Flow<T> = callbackFlow {
    val listener = subscribe { trySend(it) }

    awaitClose {
        listener.cancel()
    }
}