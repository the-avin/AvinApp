package com.avin.avinapp.utils.compose.nodes.field

import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import com.avin.avinapp.utils.compose.nodes.scrubber.Scrubber
import com.avin.avinapp.utils.compose.utils.formatSmart
import kotlinx.coroutines.flow.collectLatest
import net.objecthunter.exp4j.ExpressionBuilder
import org.jetbrains.jewel.ui.component.TextField
import kotlin.math.roundToInt

private fun calculate(text: String): Double? {
    return runCatching { ExpressionBuilder(text).build().evaluate() }.getOrNull() ?: 0.0
}

@Composable
fun ExpressionTextField(
    value: Double,
    onValueChanged: (Double) -> Unit,
    modifier: Modifier = Modifier
) {
    val state = rememberTextFieldState(value.formatSmart())

    fun updateValue(value: Double) {
        state.edit { replace(0, state.text.length, value.toString()) }
    }

    fun calculateValue() {
        if (value.formatSmart() != state.text.toString()) {
            val result = calculate(state.text.toString())?.also(onValueChanged) ?: return
            updateValue(result)
        }
    }

    LaunchedEffect(Unit) {
        snapshotFlow { state.text }.collectLatest {
            val newDouble = it.toString().toDoubleOrNull()
            onValueChanged.invoke(newDouble ?: return@collectLatest)
        }
    }

    TextField(state, modifier = modifier.onFocusChanged {
        if (!it.isFocused) {
            calculateValue()
        }
    }.onPreviewKeyEvent {
        if (it.key.keyCode == Key.Enter.keyCode) {
            calculateValue()
            true
        } else false
    }, trailingIcon = {
        Scrubber(
            onScrub = { change ->
                state.text.toString().toDoubleOrNull()
                    ?.let {
                        val newValue = it.plus(change)
                        onValueChanged.invoke(newValue)
                        updateValue(newValue)
                    }
            }
        )
    })
}

@Composable
fun ExpressionTextField(
    value: Int,
    onValueChanged: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val state = rememberTextFieldState(initialText = value.toString())

    LaunchedEffect(Unit) {
        snapshotFlow { state.text }.collectLatest {
            val newValue = it.toString().toIntOrNull()
            onValueChanged.invoke(newValue ?: return@collectLatest)
        }
    }

    fun updateValue(value: Int) {
        state.edit { replace(0, state.text.length, value.toString()) }
    }

    fun calculateValue() {
        val result = calculate(state.text.toString())
        if (result != null && result.toInt() != value) {
            onValueChanged(result.toInt())
            updateValue(result.toInt())
        }
    }

    TextField(
        state = state,
        modifier = modifier.onFocusChanged { focusState ->
            if (!focusState.isFocused) {
                calculateValue()
            }
        }.onPreviewKeyEvent {
            if (it.key.keyCode == Key.Enter.keyCode) {
                calculateValue()
                true
            } else false
        }, trailingIcon = {
            Scrubber(
                onScrub = { change ->
                    state.text.toString().toIntOrNull()
                        ?.let {
                            val newValue = it.plus(change.roundToInt())
                            onValueChanged.invoke(newValue)
                            updateValue(newValue)
                        }
                }
            )
        }
    )
}