package com.pliniodev.heimdall.permissions.delegate

import com.pliniodev.heimdall.permissions.model.PermissionState

internal interface PermissionDelegate {
    fun getPermissionState(): PermissionState
    suspend fun providePermission()
    fun openSettingPage()
}