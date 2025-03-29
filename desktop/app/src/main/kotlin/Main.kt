import androidx.compose.animation.animateContentSize
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.application
import com.avin.avinapp.manager.compose.LocalLanguageManager
import com.avin.avinapp.manager.compose.WithLocaleLanguageManager
import com.avin.avinapp.theme.AppCustomTheme
import com.avin.avinapp.theme.window.AppCustomWindow
import org.jetbrains.jewel.foundation.theme.JewelTheme
import org.jetbrains.jewel.ui.component.CheckboxRow
import org.jetbrains.jewel.ui.component.Text

@Composable
@Preview
fun App(onDismiss: () -> Unit) {
    var isChecked by remember { mutableStateOf(true) }

    WithLocaleLanguageManager {
        val languageManager = LocalLanguageManager.current
        AppCustomTheme {
            AppCustomWindow(onCloseRequest = onDismiss, title = "Avin") {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(JewelTheme.globalColors.panelBackground),
                    contentAlignment = Alignment.Center
                ) {
                    CheckboxRow(
                        checked = isChecked,
                        onCheckedChange = {
                            isChecked = it
                            if (isChecked) {
                                languageManager.load("en")
                            } else languageManager.load("fa")
                        },
                        modifier = Modifier.animateContentSize()
                    ) {
                        Text(text = if (isChecked) "Checked avin" else "Unchecked avin")
                    }
                }
            }
        }
    }
}

fun main() = application {
    App(onDismiss = ::exitApplication)
}
