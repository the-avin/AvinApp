package com.avin.avinapp.databases.providers

import com.avin.avinapp.databases.AppDatabase

interface DatabaseProvider {
    fun provideDatabase(): AppDatabase
}