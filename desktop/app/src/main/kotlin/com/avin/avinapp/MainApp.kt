package com.avin.avinapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.window.application
import com.avin.avinapp.components.RootComponent
import com.avin.avinapp.components.toFlow
import com.avin.avinapp.features.clone.page.CloneRepositoryWindow
import com.avin.avinapp.features.editor.window.ProjectEditorWindow
import com.avin.avinapp.features.new_project.page.NewProjectWindow
import com.avin.avinapp.features.projects.page.ProjectsWindow
import com.avin.avinapp.manager.compose.WithLocaleLanguageManager
import com.avin.avinapp.settings.window.SettingsWindow
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
                val newProjectSlot by rootComponent.newProjectSlot.toFlow().collectAsState(null)
                val cloneRepositorySlot by rootComponent.cloneRepositorySlot.toFlow().collectAsState(null)
                val editorSlot by rootComponent.editorSlot.toFlow().collectAsState(null)
                val settingsSlot by rootComponent.settingsSlot.toFlow().collectAsState(null)
                projectsSlot?.child?.instance?.let {
                    ProjectsWindow(
                        component = it,
                        onCloseRequest = rootComponent::closeProjects,
                        onNewProjectClick = rootComponent::openNewProject,
                        onOpenCloneRepository = rootComponent::openCloneRepository,
                        onOpenProject = rootComponent::openEditor,
                        onOpenFilePicker = rootComponent::openProjectPicker,
                        onOpenSettings = rootComponent::openSettings,
                    )
                }
                newProjectSlot?.child?.instance?.let {
                    NewProjectWindow(
                        component = it,
                        onCloseRequest = rootComponent::closeNewProject,
                    )
                }
                cloneRepositorySlot?.child?.instance?.let {
                    CloneRepositoryWindow(
                        component = it,
                        onCloseRequest = rootComponent::closeCloneRepository,
                    )
                }
                settingsSlot?.child?.instance?.let {
                    SettingsWindow(
                        component = it,
                        onCloseRequest = rootComponent::closeSettings
                    )
                }
                editorSlot?.child?.instance?.let {
                    ProjectEditorWindow(
                        component = it,
                        onNewProjectClick = rootComponent::openNewProject,
                        onCloneRepositoryClick = rootComponent::openCloneRepository,
                        onCloseRequest = rootComponent::closeEditor,
                        onOpenProject = rootComponent::openEditor,
                        onOpenSettings = rootComponent::openSettings,
                        onOpenFilePicker = rootComponent::openProjectPicker
                    )
                }
            }
        }
    }
}