package com.ionos.scanbot.util.permission

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

fun Activity.hasPermissionTo(permission: String) =
    ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

fun Activity.requestPermissionTo(permissions: Array<String>, requestCode: Int) {
    ActivityCompat.requestPermissions(
        this,
        permissions,
        requestCode
    )
}