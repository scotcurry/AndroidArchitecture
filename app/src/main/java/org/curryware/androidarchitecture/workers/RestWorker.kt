package org.curryware.androidarchitecture.workers

import org.curryware.androidarchitecture.datamodels.UEMInfo
import retrofit2.Response
import retrofit2.http.GET

interface RestWorker {

    @GET("/API/system/info")
    suspend fun getUEMInfo(): Response<UEMInfo>
}