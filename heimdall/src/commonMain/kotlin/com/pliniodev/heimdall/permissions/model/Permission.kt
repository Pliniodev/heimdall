package com.pliniodev.heimdall.permissions.model

/**
 * This enum represents the permissions used in the application.
 * It provides constant values for various permissions related to system services and features.
 * each value permission here is related to it's respective injection
 */
enum class Permission(val title: String) {

    /**
     * App location background permission.
     */
    STORAGE_READ_AND_WRITE(title = "Storage"),
}