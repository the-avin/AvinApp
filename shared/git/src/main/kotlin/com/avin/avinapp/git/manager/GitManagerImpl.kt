package com.avin.avinapp.git.manager

import com.avin.avinapp.git.helper.GitHelper
import com.avin.avinapp.git.utils.CustomProgressMonitor


class GitManagerImpl(private val gitHelper: GitHelper) : GitManager {
    override fun init(
        path: String,
        initialFiles: List<String>
    ): Boolean {
        return gitHelper.initializeRepository(path, initialFiles) != null
    }

    override fun add(path: String, vararg files: String): Boolean {
        return gitHelper.openRepository(path)?.let { git ->
            gitHelper.stageFiles(git, *files)
        } != null
    }

    override fun clone(path: String, url: String, progressMonitor: CustomProgressMonitor): Boolean {
        return gitHelper.cloneRepository(path, url, progressMonitor)
    }
}