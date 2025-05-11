package com.avin.avinapp.data.repository.editor_settings

import com.avin.avinapp.data.models.editor_settings.ProjectEditorSettings
import kotlinx.coroutines.flow.Flow

interface EditorSettingsRepository {
    fun getEditorSettings(): Flow<ProjectEditorSettings>


    companion object {
        const val DEFAULT_INSPECT_ON_HOVER_VALUE = true
    }
}