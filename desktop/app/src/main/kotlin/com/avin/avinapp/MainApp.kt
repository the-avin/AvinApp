package com.avin.avinapp

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.application
import com.avin.avinapp.components.RootComponent
import com.avin.avinapp.components.toFlow
import com.avin.avinapp.features.projects.ProjectsWindow
import com.avin.avinapp.manager.compose.WithLocaleLanguageManager
import com.avin.avinapp.preferences.AppPreferencesKeys
import com.avin.avinapp.preferences.PreferencesStorage
import com.avin.avinapp.theme.AppCustomTheme
import com.avin.avinapp.theme.window.AppCustomWindow
import org.jetbrains.jewel.foundation.theme.JewelTheme
import org.jetbrains.jewel.ui.component.CheckboxRow
import org.jetbrains.jewel.ui.component.Text
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object MainApp : KoinComponent {
    private val preferences: PreferencesStorage by inject()
    private val rootComponent: RootComponent by inject()

    @Composable
    private fun AppletInitializer(content: @Composable () -> Unit) {
        val theme by preferences.get(AppPreferencesKeys.theme).collectAsState(null)
        WithLocaleLanguageManager {
            AppCustomTheme(
                currentTheme = theme,
                content = content
            )
        }
    }

    fun init() {
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