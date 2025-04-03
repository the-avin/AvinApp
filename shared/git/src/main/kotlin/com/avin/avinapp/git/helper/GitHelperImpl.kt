package com.avin.avinapp.git.helper

import com.avin.avinapp.git.utils.CustomProgressMonitor
import org.eclipse.jgit.api.Git
import java.io.File

class GitHelperImpl : GitHelper {
    override fun initializeRepository(path: String, initialAddFiles: List<String>): Git? {
        val repoDir = File(path)
        if (!repoDir.exists()) repoDir.mkdirs()

        return runCatching {
            Git.init().setDirectory(repoDir).call().also { git ->
                git.add().apply {
                    initialAddFiles.forEach { addFilepattern(it) }
                }.call()
            }
        }.getOrElse {
            it.printStackTrace()
            null
        }
    }

    override fun openRepository(path: String): Git? {
        return runCatching {
            Git.open(File(path))
        }.getOrElse {
            it.printStackTrace()
            null
        }
    }

    override fun stageFiles(git: Git, vararg files: String): Boolean {
        return runCatching {
            git.add().apply { files.forEach { addFilepattern(it) } }.call()
            true
        }.getOrElse {
            it.printStackTrace()
            false
        }
    }

    override fun cloneRepository(path: String, url: String, progressMonitor: CustomProgressMonitor): Boolean {
        return runCatching {
            Git.cloneRepository()
                .setURI(url)
                .setDirectory(File(path))
                .setProgressMonitor(
                    progressMonitor
                )
                .call()
            true
        }.getOrElse {
            it.printStackTrace()
            false
        }
    }
}