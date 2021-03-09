package org.curryware.androidarchitecture.workers

import android.os.Build

class DeviceInformation {

    fun getBuildNumber(): Int {

        val os = Build.VERSION.SDK_INT
        return os
    }
}