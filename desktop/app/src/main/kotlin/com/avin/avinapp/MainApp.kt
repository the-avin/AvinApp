package com.avin.avinapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.window.application
import com.avin.avinapp.components.RootComponent
import com.avin.avinapp.components.toFlow
import com.avin.avinapp.features.projects.page.ProjectsWindow
import com.avin.avinapp.manager.compose.WithLocaleLanguageManager
import com.avin.avinapp.theme.AppCustomTheme
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object MainApp : KoinComponent {
    private val rootComponent: RootComponent by inject()

    @Composable
    private fun AppletInitializer(content: @Composable () -> Unit) {
        val theme by rootComponent.getTheme()
        WithLocaleLanguageManager(
            languageManager = rootComponent.languageManager
        ) {
            AppCustomTheme(
                currentTheme = theme,
                content = content
            )
        }
    }

    fun init() {
        rootComponent.initializeLanguage()
        rootComponent.openProjects()
        application {
            AppletInitializer {
                val projectsSlot by rootComponent.projectsSlot.toFlow().collectAsState(null)
                projectsSlot?.child?.instance?.let {
                    ProjectsWindow(
                        component = it,
                        onCloseRequest = rootComponent::closeProjects
                    )
                }
            }
        }
    }
}