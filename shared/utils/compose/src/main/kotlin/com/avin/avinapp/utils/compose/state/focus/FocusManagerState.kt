package com.avin.avinapp.utils.compose.state.focus

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.focus.FocusRequester
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


data class FocusItemState(
    val id: String,
    val focusRequester: FocusRequester = FocusRequester(),
    val onFocus: suspend () -> Unit
)

enum class Direction { Up, Down }

class FocusManagerState(
    private val scope: CoroutineScope
) {
    private val focusStates = mutableListOf<FocusItemState>()
    private var currentIndex: Int? = null

    fun addFocusState(id: String, onFocus: suspend () -> Unit) {
        focusStates.add(FocusItemState(id, onFocus = onFocus))
    }

    fun clear() {
        focusStates.clear()
        currentIndex = null
    }

    fun moveFocus(direction: Direction) {
        scope.launch {
            val newIndex = when (direction) {
                Direction.Up -> (currentIndex ?: 0) - 1
                Direction.Down -> (currentIndex ?: -1) + 1
            }
            if (newIndex in focusStates.indices) {
                focusStates[newIndex].apply {
                    onFocus()
                    runCatching {
                        focusRequester.requestFocus()
                    }.onSuccess {
                        currentIndex = newIndex
                    }
                }
            }
        }
    }


    fun moveFocus(id: String) {
        currentIndex = focusStates.indexOfFirst { it.id == id }
    }

    fun getFocusState(id: String): FocusItemState? = focusStates.find { it.id == id }
}

@Composable
fun rememberFocusManagerState(): FocusManagerState {
    val scope = rememberCoroutineScope()
    return remember { FocusManagerState(scope) }
}