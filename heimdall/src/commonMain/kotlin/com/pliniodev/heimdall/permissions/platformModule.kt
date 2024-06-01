package com.pliniodev.heimdall.permissions

import com.pliniodev.heimdall.permissions.service.PermissionService
import com.pliniodev.heimdall.permissions.service.PermissionServiceImpl
import org.koin.core.module.Module
import org.koin.dsl.module

internal expect fun platformModule(): Module

val permissionsModule: Module = module {
    includes(platformModule())

    single<PermissionService> {
        PermissionServiceImpl()
    }
}