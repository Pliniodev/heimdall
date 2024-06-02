package com.pliniodev.heimdall.permissions.service

import com.pliniodev.heimdall.permissions.getPermissionDelegate
import com.pliniodev.heimdall.permissions.model.Permission
import com.pliniodev.heimdall.permissions.model.PermissionState
import com.pliniodev.heimdall.permissions.service.PermissionService.Companion.MAX_PERMISSION_CHECKS
import com.pliniodev.heimdall.permissions.service.PermissionService.Companion.PERMISSION_CHECK_FLOW_FREQUENCY
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent

internal class PermissionServiceImpl : PermissionService, KoinComponent {
    override fun checkPermission(permission: Permission): PermissionState {
        return try {
            return getPermissionDelegate(permission).getPermissionState()
        } catch (e: Exception) {
            println("Failed to check permission $permission")
            e.printStackTrace()
            PermissionState.NOT_DETERMINED
        }
    }

    override suspend fun providePermission(permission: Permission) {
        try {
            getPermissionDelegate(permission).providePermission()
        } catch (e: Exception) {
            println("Failed to request permission $permission")
            e.printStackTrace()
        }
    }

    override fun checkPermissionFlow(permission: Permission): Flow<PermissionState> {
        return flow {
            repeat(MAX_PERMISSION_CHECKS) {
                val permissionState = checkPermission(permission)
                emit(permissionState)
                delay(PERMISSION_CHECK_FLOW_FREQUENCY) // talvez um broadcast receiver
            }
        }
    }

    override fun openSettingPage(permission: Permission) {
        println("Open settings for permission $permission")
        try {
            getPermissionDelegate(permission).openSettingPage()
        } catch (e: Exception) {
            println("Failed to open settings for permission $permission")
            e.printStackTrace()
        }
    }
}