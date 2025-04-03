package com.avin.avinapp.git.manager

import com.avin.avinapp.git.utils.CustomProgressMonitor

interface GitManager {
    fun init(path: String, initialFiles: List<String> = emptyList()): Boolean
    fun add(path: String, vararg files: String): Boolean
    fun clone(path: String, url: String, progressMonitor: CustomProgressMonitor): Boolean

    companion object {
        fun getRepositoryNameByURI(url: String): String {
            return url.trimEnd('/').substringAfterLast('/').removeSuffix(".git")
        }
    }
}