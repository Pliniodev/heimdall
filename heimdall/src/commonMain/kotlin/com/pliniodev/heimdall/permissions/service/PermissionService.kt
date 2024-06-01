package com.pliniodev.heimdall.permissions.service

import com.pliniodev.heimdall.permissions.model.Permission
import com.pliniodev.heimdall.permissions.model.PermissionState
import kotlinx.coroutines.flow.Flow

interface PermissionService {
    fun checkPermission(permission: Permission): PermissionState
    suspend fun providePermission(permission: Permission)
    fun checkPermissionFlow(permission: Permission): Flow<PermissionState>
    fun openSettingPage(permission: Permission)
    
    companion object {
        const val PERMISSION_CHECK_FLOW_FREQUENCY = 1000L
        const val MAX_PERMISSION_CHECKS = 10
    }
}