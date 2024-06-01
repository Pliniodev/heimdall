package com.pliniodev.heimdall.permissions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.pliniodev.heimdall.permissions.delegate.PermissionDelegate
import com.pliniodev.heimdall.permissions.model.Permission
import com.pliniodev.heimdall.permissions.service.PermissionService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

internal fun KoinComponent.getPermissionDelegate(permission: Permission): PermissionDelegate {
    val permissionDelegate by inject<PermissionDelegate>(named(permission.name))
    return permissionDelegate
}

@Composable
public fun PermissionService.collectPermissionAsState(permission: Permission) =
    checkPermissionFlow(permission).collectAsState(checkPermission(permission))