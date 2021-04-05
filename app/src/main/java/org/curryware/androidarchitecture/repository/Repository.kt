package org.curryware.androidarchitecture.repository

import org.curryware.androidarchitecture.datamodels.AccessToken
import org.curryware.androidarchitecture.datamodels.AccessUsers
import org.curryware.androidarchitecture.datamodels.UEMInfo
import org.curryware.androidarchitecture.workers.AccessRetroFitInstance
import org.curryware.androidarchitecture.workers.UEMRetroFitInstance
import retrofit2.Response

// This class is described in the app architecture document (https://developer.android.com/jetpack/guide)
// because you may have different ways to populate the ViewModels,
class Repository {


    suspend fun getUEMInfo(): Response<UEMInfo> {

        return UEMRetroFitInstance.restWorker.getUEMInfo()
    }

    suspend fun getAccessToken(headerMap: HashMap<String, String>): Response<AccessToken> {

        return AccessRetroFitInstance.restWorker.getAccessToken(headerMap)
    }

    suspend fun getAccessUsers(headerMap: HashMap<String, String>): Response<AccessUsers> {

        return AccessRetroFitInstance.restWorker.getAccessUsers(headerMap)
    }
}