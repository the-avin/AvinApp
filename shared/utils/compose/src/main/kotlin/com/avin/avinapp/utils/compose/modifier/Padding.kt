package com.avin.avinapp.utils.compose.modifier

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

private val verticalPadding = 16.dp
private val horizontalPadding = 16.dp

fun Modifier.topPadding() = padding(top = verticalPadding)
fun Modifier.bottomPadding() = padding(top = verticalPadding)
fun Modifier.startPadding() = padding(start = horizontalPadding)
fun Modifier.endPadding() = padding(start = horizontalPadding)

fun Modifier.horizontalPadding() = padding(horizontal = horizontalPadding)
fun Modifier.verticalPadding() = padding(vertical = verticalPadding)

fun Modifier.allPadding() = padding(vertical = verticalPadding, horizontal = horizontalPadding)