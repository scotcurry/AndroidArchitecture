package org.curryware.androidarchitecture.repository

import android.os.Build
import android.util.Log
import org.curryware.androidarchitecture.BuildConfig
import org.curryware.androidarchitecture.datamodels.AccessToken
import org.curryware.androidarchitecture.datamodels.Access.AccessUsers
import org.curryware.androidarchitecture.datamodels.UEMInfo
import org.curryware.androidarchitecture.workers.AccessRetroFitInstance
import org.curryware.androidarchitecture.workers.UEMRetroFitInstance
import retrofit2.Response
import java.util.*
import kotlin.collections.HashMap

// This class is described in the app architecture document (https://developer.android.com/jetpack/guide)
// because you may have different ways to populate the ViewModels,

// ALL data retrieval code should be here.  Not sure why it is called Repository.
class AccessRepository {

    private val TAG: String = "Repository"

    suspend fun getUEMInfo(): Response<UEMInfo> {

        return UEMRetroFitInstance.restWorker.getUEMInfo()
    }

    suspend fun getAccessToken(): Response<AccessToken> {

        val headerMap = HashMap<String, String>()
        headerMap["Authorization"] = buildBasicAccessHeader()
        headerMap["Content-Type"] = "application/json"
        headerMap["Accept"] = "application/json"

        val accessTokenJSON = AccessRetroFitInstance.restWorker.getAccessToken(headerMap)
        return AccessRetroFitInstance.restWorker.getAccessToken(headerMap)
    }

    suspend fun getAccessUsers(headerMap: HashMap<String, String>): Response<AccessUsers> {

        return AccessRetroFitInstance.restWorker.getAccessUsers(headerMap)
    }

    private fun buildBasicAccessHeader(): String {

        val clientID = BuildConfig.ACCESS_API_ID
        val clientToken = BuildConfig.ACCESS_API_SECRET

        val stringToConvert = "$clientID:$clientToken"
        val base64String: String = Base64.getEncoder().encodeToString(stringToConvert.toByteArray())
        val authString = "Basic $base64String"
        Log.d(TAG, authString)
        return authString
    }
}