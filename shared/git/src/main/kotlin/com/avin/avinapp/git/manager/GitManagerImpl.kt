package com.avin.avinapp.git.manager

import com.avin.avinapp.git.helper.GitHelper


class GitManagerImpl(private val gitHelper: GitHelper) : GitManager {
    override suspend fun init(
        path: String,
        initialFiles: List<String>
    ): Boolean {
        return gitHelper.initializeRepository(path, initialFiles) != null
    }

    override suspend fun add(path: String, vararg files: String): Boolean {
        return gitHelper.openRepository(path)?.let { git ->
            gitHelper.stageFiles(git, *files)
        } != null
    }
}