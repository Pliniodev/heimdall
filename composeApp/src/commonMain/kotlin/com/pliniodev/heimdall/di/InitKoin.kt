package com.pliniodev.heimdall.di

import com.pliniodev.heimdall.permissions.permissionsModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.koinApplication
import org.koin.dsl.module

fun initKoin(
    additionalModules: List<Module> = emptyList(),
): KoinApplication {
    val koinApplication = koinApplication {
        modules(
            listOf(
                module { includes(additionalModules) },
                permissionsModule,
            ),
        )
        createEagerInstances()
    }
    return startKoin(koinApplication)
}