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
private fun <T : Number> ExpressionTextFieldImpl(
    value: T,
    onValueChanged: (T) -> Unit,
    parse: (String) -> T?,
    format: (T) -> String,
    add: (T, Float) -> T,
    modifier: Modifier = Modifier
) {
    val state = rememberTextFieldState(format(value))

    fun updateValue(newValue: T) {
        state.edit {
            replace(0, state.text.length, format(newValue))
        }
    }

    fun calculateValue() {
        if (format(value) != state.text.toString()) {
            val result = calculate(state.text.toString())
            val parsed = result?.let { parse(it.toString()) } ?: return
            onValueChanged(parsed)
            updateValue(parsed)
        }
    }

    LaunchedEffect(Unit) {
        snapshotFlow { state.text }.collectLatest {
            val parsed = parse(it.toString())
            parsed?.let(onValueChanged)
        }
    }

    TextField(
        state = state,
        modifier = modifier
            .onFocusChanged {
                if (!it.isFocused) {
                    calculateValue()
                }
            }
            .onPreviewKeyEvent {
                if (it.key.keyCode == Key.Enter.keyCode) {
                    calculateValue()
                    true
                } else false
            },
        trailingIcon = {
            Scrubber(
                onScrub = { change ->
                    val current = parse(state.text.toString()) ?: return@Scrubber
                    val newValue = add(current, change)
                    onValueChanged(newValue)
                    updateValue(newValue)
                }
            )
        }
    )
}

@Composable
fun ExpressionTextField(
    value: Double,
    onValueChanged: (Double) -> Unit,
    modifier: Modifier = Modifier
) {
    ExpressionTextFieldImpl(
        value = value,
        onValueChanged = onValueChanged,
        parse = { it.toDoubleOrNull() },
        format = { "%.2f".format(it) },
        add = { v, delta -> v + delta },
        modifier = modifier
    )
}

@Composable
fun ExpressionTextField(
    value: Float,
    onValueChanged: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    ExpressionTextFieldImpl(
        value = value,
        onValueChanged = onValueChanged,
        parse = { it.toFloatOrNull() },
        format = { "%.2f".format(it) },
        add = { v, delta -> v + delta },
        modifier = modifier
    )
}

@Composable
fun ExpressionTextField(value: Int, onValueChanged: (Int) -> Unit, modifier: Modifier = Modifier) {
    ExpressionTextFieldImpl(
        value = value,
        onValueChanged = onValueChanged,
        parse = { it.toIntOrNull() },
        format = { it.toString() },
        add = { v, delta -> v + delta.roundToInt() },
        modifier = modifier
    )
}
