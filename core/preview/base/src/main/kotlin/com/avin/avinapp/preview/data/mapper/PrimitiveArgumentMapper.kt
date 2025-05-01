package com.avin.avinapp.preview.data.mapper

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.avin.avinapp.data.models.widget.ArgumentType
import com.avin.avinapp.data.models.widget.ComposableDescriptorReflection
import org.jetbrains.jewel.ui.util.fromRGBAHexStringOrNull

fun ComposableDescriptorReflection.Argument.ArgumentValue.Primitive.toRuntimeValue(
    type: ArgumentType
): Any? {
    return when (type) {
        ArgumentType.DP -> raw?.toFloat()?.dp
        ArgumentType.SP -> raw?.toFloat()?.sp
        ArgumentType.INT -> raw?.toInt()
        ArgumentType.BOOLEAN -> raw?.toBoolean()
        ArgumentType.COLOR -> raw?.let { Color.fromRGBAHexStringOrNull(it) }
        ArgumentType.STRING -> raw
        else -> null
    }
}