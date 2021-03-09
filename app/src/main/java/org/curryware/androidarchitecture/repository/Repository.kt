package org.curryware.androidarchitecture.repository

import org.curryware.androidarchitecture.datamodels.UEMInfo
import org.curryware.androidarchitecture.workers.RetroFitInstance
import retrofit2.Response

// This class is described in the app architecture document (https://developer.android.com/jetpack/guide)
// because you may have different ways to populate the ViewModels,
class Repository {

    suspend fun getUEMInfo(): Response<UEMInfo> {

        return RetroFitInstance.restWorker.getUEMInfo()
    }
}