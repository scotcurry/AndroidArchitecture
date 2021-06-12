package org.curryware.androidarchitecture.workers

import org.curryware.androidarchitecture.datamodels.AccessToken
import org.curryware.androidarchitecture.datamodels.Access.AccessUsers
import org.curryware.androidarchitecture.datamodels.UEMInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface RestWorker {

    @GET("/API/system/info")
    suspend fun getUEMInfo(): Response<UEMInfo>

    @POST("/SAAS/auth/oauthtoken?grant_type=client_credentials")
    suspend fun getAccessToken(@HeaderMap headers: Map<String, String>): Response<AccessToken>

    @GET("/SAAS/jersey/manager/api/scim/Users")
    suspend fun getAccessUsers(@HeaderMap headers: Map<String, String>): Response<AccessUsers>
}