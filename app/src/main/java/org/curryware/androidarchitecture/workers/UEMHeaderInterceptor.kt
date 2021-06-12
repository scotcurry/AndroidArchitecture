package org.curryware.androidarchitecture.workers

import android.os.Build
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.curryware.androidarchitecture.BuildConfig
import java.util.*

class UEMHeaderInterceptor : Interceptor {

    private val TAG: String = "UEMHeaderInterceptor"

    override fun intercept(chain: Interceptor.Chain): Response {

        val authHeader = buildBasicUEMHeader()
        val awTenantCode = BuildConfig.UEM_TENANT_CODE

        val request: Request = chain.request()
            .newBuilder()
            .addHeader("Authorization", authHeader)
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            .addHeader("aw-tenant-code", awTenantCode)
            .build()

        return chain.proceed(request)
    }

    private fun buildBasicUEMHeader(): String {

        val userName = BuildConfig.UEM_API_USERNAME
        val password = BuildConfig.UEM_API_PASSWORD

        val stringToConvert = "$userName:$password"
        val base64String: String = Base64.getEncoder().encodeToString(stringToConvert.toByteArray())
        val authString = "Basic $base64String"
        Log.d(TAG, authString)
        return authString
    }
}