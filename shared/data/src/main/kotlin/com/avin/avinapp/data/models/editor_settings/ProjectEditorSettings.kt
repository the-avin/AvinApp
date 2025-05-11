package com.avin.avinapp.data.models.editor_settings

import androidx.compose.runtime.Immutable
import com.avin.avinapp.data.repository.editor_settings.EditorSettingsRepository

@Immutable
data class ProjectEditorSettings(
    val snapshotPreview: SnapshotPreview = SnapshotPreview()
) {
    @Immutable
    data class SnapshotPreview(
        val inspectOnHover: Boolean = EditorSettingsRepository.DEFAULT_INSPECT_ON_HOVER_VALUE
    )
}