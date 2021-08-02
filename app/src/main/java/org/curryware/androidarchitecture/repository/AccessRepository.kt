package org.curryware.androidarchitecture.repository

import android.util.Log
import org.curryware.androidarchitecture.BuildConfig
import org.curryware.androidarchitecture.datamodels.Access.AccessRestApi.AccessToken
import org.curryware.androidarchitecture.datamodels.Access.AccessRestApi.AccessUser
import org.curryware.androidarchitecture.datamodels.AccessUserForView
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

    private val TAG: String = "AccessRepository"

    suspend fun getUEMInfo(): Response<UEMInfo> {

        return UEMRetroFitInstance.restWorker.getUEMInfo()
    }

    suspend fun getAccessToken(): Response<AccessToken> {

        val headerMap = HashMap<String, String>()
        headerMap["Authorization"] = buildBasicAccessHeader()
        headerMap["Content-Type"] = "application/json"
        headerMap["Accept"] = "application/json"

        return AccessRetroFitInstance.restWorker.getAccessToken(headerMap)
    }

    suspend fun getAccessUsers(accessToken: String?): Response<AccessUser> {

        val headerMap = HashMap<String, String>()
        headerMap["Authorization"] = "HZN $accessToken"
        headerMap["Content-Type"] = "application/json"
        headerMap["Accept"] = "application/json"

        var allUsers: MutableList<AccessUserForView>? = null
        val response = AccessRetroFitInstance.restWorker.getAccessUsers(headerMap)
        val responseBody = response.body()?.Resources
        // Log.d(TAG, "Response: $responseBody")
        if (responseBody != null) {
            for (currentResource in responseBody) {
                val userName = currentResource.userName
                val firstName = currentResource.name.givenName
                val lastName = currentResource.name.familyName
                val email = currentResource.emails[0].value
                val title = currentResource.title
                val fullName = "$firstName $lastName"
                val userToAdd = AccessUserForView(userName, fullName, email, title)
                allUsers?.add(userToAdd)
            }
        }
        return AccessRetroFitInstance.restWorker.getAccessUsers(headerMap)
    }

    private fun buildBasicAccessHeader(): String {

        // These values are stored in the apikey.properties file at the root of the project.
        // and pulled in to the project in the module build.gradle file.
        val clientID = BuildConfig.ACCESS_API_ID
        val clientToken = BuildConfig.ACCESS_API_SECRET

        val stringToConvert = "$clientID:$clientToken"
        val base64String: String = Base64.getEncoder().encodeToString(stringToConvert.toByteArray())
        val authString = "Basic $base64String"
        // Log.d(TAG, authString)
        return authString
    }
}