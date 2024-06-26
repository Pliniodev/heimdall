package com.pliniodev.heimdall.permissions.delegate

import com.pliniodev.heimdall.permissions.model.PermissionState
import com.pliniodev.heimdall.permissions.openAppSettingsPage

internal class StoragePermissionDelegate() : PermissionDelegate {
    override fun getPermissionState(): PermissionState {
        // On iOS, there are no specific storage permissions to request
        return PermissionState.GRANTED
    }

    override suspend fun providePermission() {}

    override fun openSettingPage() {
        openAppSettingsPage()
    }
}