package com.avin.avinapp.git.helper

import com.avin.avinapp.git.utils.CustomProgressMonitor
import org.eclipse.jgit.api.Git

interface GitHelper {
    fun initializeRepository(path: String, initialAddFiles: List<String>): Git?
    fun openRepository(path: String): Git?
    fun stageFiles(git: Git, vararg files: String): Boolean
    fun cloneRepository(path: String, url: String, progressMonitor: CustomProgressMonitor): Boolean
}