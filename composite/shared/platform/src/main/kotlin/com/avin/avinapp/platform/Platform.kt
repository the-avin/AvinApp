package com.avin.avinapp.platform

sealed class Platform {
    object Windows : Platform()
    object Linux : Platform()
    object Mac : Platform()

    companion object {
        val current: Platform by lazy {
            val osName = System.getProperty("os.name").lowercase()
            when {
                osName.contains("win") -> Windows
                osName.contains("mac") -> Mac
                osName.contains("nix") || osName.contains("nux") || osName.contains("aix") -> Linux
                else -> throw IllegalStateException("Unknown OS")
            }
        }

        fun isMac() = current is Mac
        fun isWindows() = current is Windows
        fun isLinux() = current is Linux
    }
}