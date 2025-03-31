package com.avin.avinapp.theme.compositions

import androidx.compose.runtime.compositionLocalOf
import java.awt.Window

val LocalWindow = compositionLocalOf<Window> { error("not provided") }