package com.avin.avinapp.utils.compose.nodes.text

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.avin.avinapp.resource.Resource
import org.jetbrains.jewel.foundation.theme.JewelTheme
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.jewel.ui.component.painterResource

@Composable
fun WarningMessage(message: String, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        Image(
            painter = painterResource(Resource.image.WARNING),
            contentDescription = null,
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = message,
            color = JewelTheme.globalColors.text.error
        )
    }
}