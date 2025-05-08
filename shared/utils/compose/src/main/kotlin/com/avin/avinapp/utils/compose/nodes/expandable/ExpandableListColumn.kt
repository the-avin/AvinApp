package com.avin.avinapp.utils.compose.nodes.expandable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.avin.avinapp.utils.compose.modifier.startPadding

@Composable
fun <T> ExpandableListColumn(
    items: Map<String, List<T>>,
    modifier: Modifier = Modifier,
    initialExpandedIndex: Int = 0,
    topContent: (@Composable ColumnScope.() -> Unit)? = null,
    content: @Composable (T) -> Unit
) {
    Column(modifier) {
        topContent?.invoke(this)
        items.toList().forEachIndexed { index, (title, list) ->
            var isExpanded by remember { mutableStateOf(index == initialExpandedIndex) }
            ExpandableTitle(
                title,
                isExpanded = isExpanded,
                onExpandChanged = { isExpanded = it }
            )
            AnimatedVisibility(isExpanded) {
                Column(modifier = Modifier.startPadding()) {
                    list.forEach {
                        content.invoke(it)
                    }
                }
            }
        }
    }
}