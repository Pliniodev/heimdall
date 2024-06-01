package com.pliniodev.heimdall.permissions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.core.app.ActivityCompat
import com.pliniodev.heimdall.permissions.model.Permission
import com.pliniodev.heimdall.permissions.model.PermissionState

private const val PERMISSION_REQUEST_CODE = 100

internal fun Context.openPage(
    action: String,
    newData: Uri? = null,
    onError: (Exception) -> Unit,
) {
    try {
        val intent = Intent(action).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            newData?.let { data = it }
        }
        startActivity(intent)
    } catch (e: Exception) {
        onError(e)
    }
}

internal fun checkPermissions(
    context: Context,
    activity: Lazy<Activity>,
    permissions: List<String>,
): PermissionState {
    
    permissions.ifEmpty { return PermissionState.GRANTED } // no permissions needed
    val status = permissions.map(context::checkSelfPermission) 
    val isAllGranted: Boolean = status.all { it == PackageManager.PERMISSION_GRANTED }
    if (isAllGranted) return PermissionState.GRANTED
    val isAllRequestRationale = checkRequestPermissionRationale(activity, permissions)
    
    return if (isAllRequestRationale) PermissionState.NOT_DETERMINED
    else PermissionState.DENIED
}

internal fun Activity.providePermissions(
    permissions: List<String>,
    onError: (Throwable) -> Unit,
) {
    try {
        ActivityCompat.requestPermissions(
            this, permissions.toTypedArray(), PERMISSION_REQUEST_CODE
        )
    } catch (t: Throwable) {
        onError(t)
    }
}

internal fun Context.openAppSettingsPage(permission: Permission) {
    openPage(
        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        newData = Uri.parse("package:$packageName"),
        onError = { throw CannotOpenSettingsException(permission.name) }
    )
}

/**
 * Checks whether a rationale should be shown for requesting permission to the user for each permission in the provided list.
 *
 * @param activity A Lazy instance of Activity representing the activity where the check is being performed.
 * @param permissions A list of strings containing the permissions to check if rationale should be shown.
 * @return true if a rationale should be shown to the user for all permissions in the list, otherwise false.
 */
private fun checkRequestPermissionRationale(
    activity: Lazy<Activity>,
    permissions: List<String>,
): Boolean =
    try {
        permissions.all { activity.value.shouldShowRequestPermissionRationale(it).not() }
    } catch (t: Throwable) {
        t.printStackTrace()
        true
    }
