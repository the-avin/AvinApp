package com.avin.avinapp.pages

import kotlinx.serialization.Serializable

sealed class AppPages(val key: String) {
    @Serializable
    data object Projects : AppPages("projects")
}