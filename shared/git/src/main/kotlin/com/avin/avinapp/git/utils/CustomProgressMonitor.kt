package com.avin.avinapp.git.utils

import org.eclipse.jgit.lib.ProgressMonitor

class CustomProgressMonitor(
    private val beginTask: (title: String, totalWork: Int) -> Unit = { title, totalWork ->
        "Task: $title (Total Work: $totalWork)"
    },
    private val onCompletedTasksUpdate: (completed: Int) -> Unit = {},
    private val onEnd: () -> Unit,
    private val onStart: (totalTasks: Int) -> Unit = {},
) : ProgressMonitor {
    override fun start(totalTasks: Int) {
        onStart.invoke(totalTasks)
    }

    override fun beginTask(title: String?, totalWork: Int) {
        beginTask.invoke(title.orEmpty(), totalWork)
    }

    override fun update(completed: Int) {
        onCompletedTasksUpdate.invoke(completed)
    }

    override fun endTask() {
        onEnd.invoke()
    }

    override fun isCancelled(): Boolean {
        return false
    }

    override fun showDuration(p0: Boolean) {}
}