package com.avin.avinapp.utils.compose.modifier

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

private val verticalPadding = 16.dp
private val horizontalPadding = 16.dp

fun Modifier.topPadding() = padding(top = verticalPadding)
fun Modifier.bottomPadding() = padding(bottom = verticalPadding)
fun Modifier.startPadding() = padding(start = horizontalPadding)
fun Modifier.endPadding() = padding(end = horizontalPadding)

fun Modifier.horizontalPadding() = padding(horizontal = horizontalPadding)
fun Modifier.verticalPadding() = padding(vertical = verticalPadding)
fun Modifier.allPadding() = padding(vertical = verticalPadding, horizontal = horizontalPadding)

// PaddingValues for use in contentPadding parameters
val topPaddingValues: PaddingValues
    get() = PaddingValues(top = verticalPadding)

val bottomPaddingValues: PaddingValues
    get() = PaddingValues(bottom = verticalPadding)

val startPaddingValues: PaddingValues
    get() = PaddingValues(start = horizontalPadding)

val endPaddingValues: PaddingValues
    get() = PaddingValues(end = horizontalPadding)

val horizontalPaddingValues: PaddingValues
    get() = PaddingValues(horizontal = horizontalPadding)

val verticalPaddingValues: PaddingValues
    get() = PaddingValues(vertical = verticalPadding)

val allPaddingValues: PaddingValues
    get() = PaddingValues(horizontal = horizontalPadding, vertical = verticalPadding)