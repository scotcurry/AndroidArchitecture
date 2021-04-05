package org.curryware.androidarchitecture.workers

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.util.*

class AccessHeaderInterceptor : Interceptor {

    private val TAG: String = "AccessHeaderInterceptor"

    override fun intercept(chain: Interceptor.Chain): Response {

        val authHeader = buildBasicAccessHeader()

        val request: Request = chain.request()
                .newBuilder()
                // .addHeader("Authorization", authHeader)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                // .addHeader("aw-tenant-code", "fq4Bi1p7DHsSpjxgeHdvWf41YEkpZ3PCIEbClTwj7mY=")
                .build()

        return chain.proceed(request)
    }

    private fun buildBasicAccessHeader(): String {

        val clientID = "Zero_Day_Token"
        val clientToken = "CTOpIVfg7sMJiNtSzdMZTHDMXXNGluv8mTo0NMgoF6PGXtqR"

        val stringToConvert = "$clientID:$clientToken"
        val base64String: String = Base64.getEncoder().encodeToString(stringToConvert.toByteArray())
        val authString = "Basic $base64String"
        Log.d(TAG, authString)
        return authString
    }
}