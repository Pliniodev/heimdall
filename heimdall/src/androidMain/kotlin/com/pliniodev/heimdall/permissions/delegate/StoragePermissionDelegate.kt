package com.pliniodev.heimdall.permissions.delegate

import android.Manifest
import android.app.Activity
import android.content.Context
import android.os.Build
import com.pliniodev.heimdall.permissions.PermissionRequestException
import com.pliniodev.heimdall.permissions.model.Permission
import com.pliniodev.heimdall.permissions.model.PermissionState
import com.pliniodev.heimdall.permissions.checkPermissions
import com.pliniodev.heimdall.permissions.openAppSettingsPage
import com.pliniodev.heimdall.permissions.providePermissions

internal class StoragePermissionDelegate(
    private val context: Context,
    private val activity: Lazy<Activity>,
) : PermissionDelegate {
    override fun getPermissionState(): PermissionState {
        return checkPermissions(context, activity, getStoragePermissions())
    }

    override suspend fun providePermission() {
        activity.value.providePermissions(getStoragePermissions()) {
            throw PermissionRequestException(Permission.STORAGE_READ_AND_WRITE.name)
        }
    }

    override fun openSettingPage() {
        context.openAppSettingsPage(Permission.STORAGE_READ_AND_WRITE)
    }

    /**
     * get correct permissions by sdk version
     * https://developer.android.com/reference/android/Manifest.permission
     */
    private fun getStoragePermissions(): List<String> {
        val readPermissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            listOf(
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.READ_MEDIA_AUDIO,
                Manifest.permission.READ_MEDIA_VIDEO,
            )
        } else listOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
        )
        val writePermissions = if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
            listOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        } else emptyList()
        
        return readPermissions + writePermissions
    }
}
