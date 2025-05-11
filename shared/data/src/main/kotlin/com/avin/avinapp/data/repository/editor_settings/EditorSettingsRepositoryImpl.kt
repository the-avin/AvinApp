package com.avin.avinapp.data.repository.editor_settings

import com.avin.avinapp.data.models.editor_settings.ProjectEditorSettings
import com.avin.avinapp.data.repository.editor_settings.EditorSettingsRepository.Companion.DEFAULT_INSPECT_ON_HOVER_VALUE
import com.avin.avinapp.preferences.AppPreferencesKeys
import com.avin.avinapp.preferences.PreferencesStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class EditorSettingsRepositoryImpl(
    private val preferences: PreferencesStorage
) : EditorSettingsRepository {

    override fun getEditorSettings(): Flow<ProjectEditorSettings> {
        return combine(
            provideSnapshotPreviewSettings()
        ) { flows ->
            ProjectEditorSettings(
                snapshotPreview = flows[0]
            )
        }
    }

    private fun provideSnapshotPreviewSettings(): Flow<ProjectEditorSettings.SnapshotPreview> {
        return preferences.get(AppPreferencesKeys.inspectOnHover).map { inspectOnHover ->
            ProjectEditorSettings.SnapshotPreview(
                inspectOnHover = inspectOnHover ?: DEFAULT_INSPECT_ON_HOVER_VALUE
            )
        }
    }
}