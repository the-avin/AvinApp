package com.avin.avinapp.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.avin.avinapp.MainApp
import com.avin.avinapp.features.projects.component.ProjectsComponent
import com.avin.avinapp.manager.language.LanguageManager
import com.avin.avinapp.pages.AppPages
import com.avin.avinapp.preferences.AppPreferencesKeys
import com.avin.avinapp.preferences.PreferencesStorage
import com.avin.avinapp.resource.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject

class RootComponent(context: ComponentContext) : BaseComponent(context), KoinComponent {
    val languageManager: LanguageManager by inject()
    private val preferences: PreferencesStorage by inject()

    private val projects = SlotNavigation<AppPages.Projects>()
    val projectsSlot = childSlot(
        source = projects,
        serializer = null,
        key = AppPages.Projects.key,
        childFactory = { _, parentComponent -> ProjectsComponent(context = parentComponent, repository = get()) }
    )

    fun openProjects() {
        projects.activate(AppPages.Projects)
    }

    fun closeProjects() {
        projects.dismiss()
    }

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


    @Composable
    fun getTheme() = preferences.get(AppPreferencesKeys.theme).collectAsState(null)
}