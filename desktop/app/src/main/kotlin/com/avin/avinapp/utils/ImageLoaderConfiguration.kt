package com.avin.avinapp.utils

import coil3.ImageLoader
import coil3.SingletonImageLoader
import coil3.network.ktor3.KtorNetworkFetcherFactory

object ImageLoaderConfiguration {
    fun configure() {
        SingletonImageLoader.setSafe {
            ImageLoader.Builder(it)
                .components {
                    add(KtorNetworkFetcherFactory())
                }
                .build()
        }
    }
}