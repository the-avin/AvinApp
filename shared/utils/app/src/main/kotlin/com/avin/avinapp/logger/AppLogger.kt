package com.avin.avinapp.logger

import androidx.compose.runtime.snapshots.Snapshot
import java.util.logging.ConsoleHandler
import java.util.logging.Level
import java.util.logging.Logger
import java.util.logging.SimpleFormatter

object AppLogger {
    @Volatile
    var isEnabled: Boolean = true

    private val nativeLogger: Logger by lazy {
        Logger.getLogger(AppLogger::class.simpleName).applyLogLevels()
    }

    fun debug(tag: String, message: String, throwable: Throwable? = null) {
        log(Level.FINE, tag, message, throwable)
    }

    fun info(tag: String, message: String, throwable: Throwable? = null) {
        log(Level.INFO, tag, message, throwable)
    }

    fun warning(tag: String, message: String, throwable: Throwable? = null) {
        log(Level.WARNING, tag, message, throwable)
    }

    fun error(tag: String, message: String, throwable: Throwable? = null) {
        log(Level.SEVERE, tag, message, throwable)
    }


    fun error(tag: String, throwable: Throwable? = null) {
        log(Level.SEVERE, tag, null, throwable)
    }

    private fun log(level: Level, tag: String, message: String?, throwable: Throwable?) {
        if (!isEnabled) return

        Snapshot.withoutReadObservation {
            when {
                throwable != null && message != null -> {
                    nativeLogger.log(level, "[$tag] -> $message", throwable)
                }

                throwable != null -> {
                    nativeLogger.log(level, "[$tag]", throwable)
                }

                message != null -> {
                    nativeLogger.log(level, "[$tag] -> $message")
                }
            }
        }
    }
}


private fun Logger.applyLogLevels(): Logger = apply {
    level = Level.FINE
    useParentHandlers = false

    handlers.forEach { removeHandler(it) }

    val consoleHandler = ConsoleHandler().apply {
        level = Level.FINE
        formatter = SimpleFormatter()
    }
    addHandler(consoleHandler)
}