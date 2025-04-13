package com.avin.avinapp.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.childSlot
import com.avin.avinapp.core.loader.ProjectLoader
import com.avin.avinapp.data.repository.project.ProjectRepository
import com.avin.avinapp.features.clone.component.CloneRepositoryComponent
import com.avin.avinapp.features.editor.component.ProjectEditorComponent
import com.avin.avinapp.features.new_project.component.NewProjectComponent
import com.avin.avinapp.features.projects.component.ProjectsComponent
import com.avin.avinapp.manager.language.LanguageManager
import com.avin.avinapp.pages.AppPages
import com.avin.avinapp.preferences.AppPreferencesKeys
import com.avin.avinapp.preferences.PreferencesStorage
import com.avin.avinapp.resource.Resource
import com.avin.avinapp.settings.component.SettingsComponent
import com.avin.avinapp.utils.SlotPageManager
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.dialogs.openDirectoryPicker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject

class RootComponent(context: ComponentContext) : BaseComponent(context), KoinComponent {

    private val preferences: PreferencesStorage by inject()
    val languageManager: LanguageManager by inject()
    private val projectLoader: ProjectLoader by inject()
    private val projectRepository: ProjectRepository by inject()

    // Pages
    private val projects = SlotPageManager<AppPages.Projects>()
    private val newProject = SlotPageManager<AppPages.NewProject>()
    private val cloneRepository = SlotPageManager<AppPages.CloneRepository>()
    private val editor = SlotPageManager<AppPages.Editor>()
    private val settings = SlotPageManager<AppPages.Settings>()

    // Slots
    val projectsSlot = childSlot(
        source = projects.navigation,
        serializer = null,
        key = AppPages.Projects.key,
        childFactory = { _, ctx ->
            ProjectsComponent(
                context = ctx,
                repository = projectRepository
            )
        }
    )

    val newProjectSlot = childSlot(
        source = newProject.navigation,
        serializer = null,
        key = AppPages.NewProject.key,
        childFactory = { _, ctx ->
            NewProjectComponent(
                context = ctx,
                repository = projectRepository
            )
        }
    )

    val cloneRepositorySlot = childSlot(
        source = cloneRepository.navigation,
        serializer = null,
        key = AppPages.CloneRepository.key,
        childFactory = { _, ctx ->
            CloneRepositoryComponent(
                context = ctx,
                repository = projectRepository
            )
        }
    )

    val settingsSlot = childSlot(
        source = settings.navigation,
        serializer = null,
        key = AppPages.Settings.key,
        childFactory = { config, ctx -> SettingsComponent(context = ctx, provider = get()) }
    )

    val editorSlot = childSlot(
        source = editor.navigation,
        serializer = null,
        key = AppPages.Editor.KEY,
        childFactory = { config, ctx ->
            ProjectEditorComponent(
                context = ctx,
                info = config,
                repository = projectRepository,
                devicesRepository = get()
            )
        }
    )

    // Navigation functions
    fun openProjects() = projects.open(AppPages.Projects)
    fun closeProjects() = projects.close()

    fun openNewProject() = newProject.open(AppPages.NewProject)
    fun closeNewProject() = newProject.close()

    fun openCloneRepository() = cloneRepository.open(AppPages.CloneRepository)
    fun closeCloneRepository() = cloneRepository.close()

    fun openSettings() = settings.open(AppPages.Settings)
    fun closeSettings() = settings.close()

    fun openEditor(projectId: Long) {
        closeProjects()
        editor.open(AppPages.Editor(projectId))
    }

    fun closeEditor() {
        openProjects()
        editor.close()
    }

    // Language management
    private fun collectToLanguage() = scope.launch {
        preferences.get(AppPreferencesKeys.language).collectLatest {
            if (it != languageManager.container.value?.currentLanguage) {
                languageManager.load(language = it)
                loadAppName()
            }
        }
    }

    fun initializeLanguage() {
        runBlocking(Dispatchers.IO) {
            preferences.get(AppPreferencesKeys.language).firstOrNull().let {
                languageManager.load(language = it)
                loadAppName()
            }
        }
        collectToLanguage()
    }

    private fun loadAppName() {
        System.setProperty("apple.awt.application.name", languageManager.getText(Resource.string.appName))
    }


    // Project picker
    fun openProjectPicker() = scope.launch(Dispatchers.IO) {
        val directory = FileKit.openDirectoryPicker()
        directory?.file?.let { file ->
            if (projectLoader.isValidProject(file)) {
                val manifest = projectLoader.loadManifest(file.path)
                val id = projectRepository.insertProject(manifest.project.name, file.path)
                openEditor(id)
            }
        }
    }

    @Composable
    fun getTheme() = preferences.get(AppPreferencesKeys.theme).collectAsState(null)
}