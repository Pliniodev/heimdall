package com.pliniodev.heimdall.permissions

import com.pliniodev.heimdall.permissions.delegate.PermissionDelegate
import com.pliniodev.heimdall.permissions.delegate.StoragePermissionDelegate
import com.pliniodev.heimdall.permissions.model.Permission
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal actual fun platformModule(): Module = module {
    single<PermissionDelegate>(named(Permission.STORAGE_READ_AND_WRITE.name)) {
        StoragePermissionDelegate()
    }
}