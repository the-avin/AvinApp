package com.avin.avinapp.utils.compose.hooks

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.focus.FocusRequester

val LocalFocusRequester =
    compositionLocalOf<FocusRequester> { error("LocalFocusRequester not provided") }


@Composable
fun NewFocusRequester(focusRequester: FocusRequester, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalFocusRequester provides focusRequester, content = content)
}