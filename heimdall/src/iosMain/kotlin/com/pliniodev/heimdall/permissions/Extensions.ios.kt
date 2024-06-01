package com.pliniodev.heimdall.permissions

import com.pliniodev.heimdall.permissions.model.Permission
import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import platform.UIKit.UIApplicationOpenSettingsURLString
import platform.UIKit.UIViewController

fun openNSUrl(string: String) {
    val settingsUrl: NSURL = NSURL.URLWithString(string)!!
    if (UIApplication.sharedApplication.canOpenURL(settingsUrl)) {
        UIApplication.sharedApplication.openURL(settingsUrl)
    } else throw CannotOpenSettingsException(string)
}

internal fun openAppSettingsPage() {
    openNSUrl(UIApplicationOpenSettingsURLString)
}

internal fun UIViewController.openPage(
    action: String,
    onError: (Exception) -> Unit,
) {
    try {
        val url = NSURL(string = action)
        UIApplication.sharedApplication.openURL(url)
    } catch (e: Exception) {
        onError(e)
    }
}
