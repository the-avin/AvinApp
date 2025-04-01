package com.avin.avinapp.di

import com.avin.avinapp.git.helper.GitHelper
import com.avin.avinapp.git.helper.GitHelperImpl
import com.avin.avinapp.git.manager.GitManager
import com.avin.avinapp.git.manager.GitManagerImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val otherModules = module {
    singleOf(::GitHelperImpl).bind<GitHelper>()
    singleOf(::GitManagerImpl).bind<GitManager>()
}