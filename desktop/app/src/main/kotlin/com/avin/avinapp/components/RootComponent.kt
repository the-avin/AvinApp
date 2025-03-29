package com.avin.avinapp.components

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.avin.avinapp.features.projects.component.ProjectsComponent
import com.avin.avinapp.pages.AppPages
import org.koin.core.component.KoinComponent

class RootComponent(context: ComponentContext) : BaseComponent(context), KoinComponent {
    private val projects = SlotNavigation<AppPages.Projects>()
    val projectsSlot = childSlot(
        source = projects,
        serializer = null,
        key = AppPages.Projects.key,
        childFactory = { _, parentComponent -> ProjectsComponent(parentComponent) }
    )

    fun openProjects() {
        projects.activate(AppPages.Projects)
    }

    fun closeProjects() {
        projects.dismiss()
    }
}