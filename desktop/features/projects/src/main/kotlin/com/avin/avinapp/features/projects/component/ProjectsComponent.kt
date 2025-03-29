package com.avin.avinapp.features.projects.component

import androidx.compose.foundation.text.input.TextFieldState
import com.arkivanov.decompose.ComponentContext
import com.avin.avinapp.components.BaseComponent

class ProjectsComponent(context: ComponentContext) : BaseComponent(context) {
    val searchState = TextFieldState()
}