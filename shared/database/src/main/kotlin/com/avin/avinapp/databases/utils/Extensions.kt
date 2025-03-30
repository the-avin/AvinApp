package com.avin.avinapp.databases.utils

import app.cash.sqldelight.Query
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.CONFLATED
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@JvmName("toFlow")
fun <T : Any> Query<T>.asFlow(): Flow<Query<T>> = flow {
    emit(this@asFlow)

    val channel = Channel<Unit>(CONFLATED)
    val listener = object : Query.Listener {
        override fun queryResultsChanged() {
            channel.trySend(Unit)
        }
    }
    addListener(listener)
    try {
        for (item in channel) {
            emit(this@asFlow)
        }
    } finally {
        removeListener(listener)
    }
}