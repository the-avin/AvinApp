package com.avin.avinapp.utils.compose.nodes.expandable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import com.avin.avinapp.utils.compose.hooks.NewFocusRequester
import com.avin.avinapp.utils.compose.modifier.focus.onVerticalFocusKeyEvent
import com.avin.avinapp.utils.compose.modifier.startPadding
import com.avin.avinapp.utils.compose.state.focus.rememberFocusManagerState

private fun getId(index: Int, subIndex: Int) = "item_${index}_$subIndex"

@Composable
fun <T> ExpandableListColumn(
    items: Map<String, List<T>>,
    modifier: Modifier = Modifier,
    initialExpandedIndex: Int = 0,
    topContent: (@Composable ColumnScope.() -> Unit)? = null,
    content: @Composable (T) -> Unit
) {
    val focusManagerState = rememberFocusManagerState()
    val expandedStates = remember(items) {
        items.keys.mapIndexed { index, key ->
            key to mutableStateOf(false)
        }.toMap()
    }

    LaunchedEffect(items) {
        val initialItemKey = items.keys.toList().getOrNull(initialExpandedIndex)
        initialItemKey?.let {
            expandedStates[it]?.value = true
        }
        focusManagerState.clear()
        items.toList().forEachIndexed { index, (key, list) ->
            list.forEachIndexed { subIndex, _ ->
                focusManagerState.addFocusState(getId(index, subIndex)) {
                    if (expandedStates[key]?.value != true) {
                        expandedStates[key]?.value = true
                        withFrameNanos { }
                    }
                }
            }
        }
    }

    Column(modifier.onVerticalFocusKeyEvent(focusManagerState::moveFocus)) {
        topContent?.invoke(this)
        items.toList().forEachIndexed { index, (title, list) ->
            val key = items.keys.elementAt(index)
            val isExpandedState = expandedStates[key] ?: remember { mutableStateOf(false) }
            ExpandableTitle(
                title,
                isExpanded = isExpandedState.value,
                onExpandChanged = { isExpandedState.value = it }
            )
            AnimatedVisibility(
                isExpandedState.value,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Column(modifier = Modifier.startPadding()) {
                    list.forEachIndexed { subIndex, item ->
                        val itemId = getId(index, subIndex)
                        val focusState = focusManagerState.getFocusState(itemId)
                        focusState?.let { state ->
                            Box(
                                Modifier.onFocusChanged {
                                    if (it.hasFocus) focusManagerState.moveFocus(state.id)
                                }
                            ) {
                                NewFocusRequester(state.focusRequester) {
                                    content(item)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}