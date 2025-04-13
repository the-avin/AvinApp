package com.avin.avinapp.data.models.settings.config

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import com.avin.avinapp.locale.StringRes
import kotlinx.coroutines.flow.Flow


sealed class SettingsType<T> {
    data object Checkbox : SettingsType<Boolean>()
    data class Enum(val values: List<StringRes>) : SettingsType<String>()
}

@Immutable
data class SettingsConfiguration<T : Any>(
    val name: StringRes,
    val initialValues: Flow<T?>,
    val defaultValue: @Composable () -> T,
    val type: SettingsType<T>,
    val hint: (@Composable (T) -> StringRes)? = null,
    val onValueChange: suspend (T) -> Unit,
)