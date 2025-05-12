package com.avin.avinapp.features.editor.widgets.descriptor_list

import androidx.compose.foundation.background
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import com.avin.avinapp.compose.dnd.modifiers.dragSource
import com.avin.avinapp.compose.dnd.state.DragAndDropState
import com.avin.avinapp.data.models.descriptor.composable.ComposableDescriptor
import com.avin.avinapp.utils.compose.hooks.LocalFocusRequester
import com.avin.avinapp.utils.compose.modifier.focus.focusWhenPressed
import org.jetbrains.jewel.foundation.theme.JewelTheme
import org.jetbrains.jewel.foundation.theme.LocalContentColor
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.jewel.ui.theme.simpleListItemStyle

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ComposableDescriptorItem(
    descriptor: ComposableDescriptor,
    dragAndDropState: DragAndDropState,
) {
    val style = JewelTheme.simpleListItemStyle
    val ins = remember { MutableInteractionSource() }
    val isHovered by ins.collectIsHoveredAsState()
    val isFocused by ins.collectIsFocusedAsState()
    val isDragging by ins.collectIsDraggedAsState()
    val color = when {
        isFocused && !isDragging -> style.colors.backgroundSelectedFocused
        isHovered -> JewelTheme.contentColor.copy(.1f)
        else -> Color.Transparent
    }
    val focusRequester = LocalFocusRequester.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .focusWhenPressed(interactionSource = ins, focusRequester = focusRequester)
            .dragSource(dragAndDropState, descriptor, interactionSource = ins)
            .fillMaxWidth()
            .height(JewelTheme.globalMetrics.rowHeight)
            .hoverable(ins)
            .padding(style.metrics.outerPadding)
            .background(color, RoundedCornerShape(style.metrics.selectionBackgroundCornerSize))
            .padding(style.metrics.innerPadding),
    ) {
        Text(
            text = buildAnnotatedString {
                append(descriptor.name)
                withStyle(SpanStyle(color = LocalContentColor.current.copy(.7f))) {
                    append(" - ${descriptor.descriptorKey}")
                }
            },
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = JewelTheme.defaultTextStyle
        )
    }
}