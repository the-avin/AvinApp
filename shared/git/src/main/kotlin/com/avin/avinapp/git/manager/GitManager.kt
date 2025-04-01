package com.avin.avinapp.git.manager

interface GitManager {
    suspend fun init(path: String, initialFiles: List<String> = emptyList()): Boolean
    suspend fun add(path: String, vararg files: String): Boolean
}