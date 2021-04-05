package org.curryware.androidarchitecture.workers

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.util.*

class UEMHeaderInterceptor : Interceptor {

    private val TAG: String = "UEMHeaderInterceptor"

    override fun intercept(chain: Interceptor.Chain): Response {

        val authHeader = buildBasicUEMHeader()

        val request: Request = chain.request()
            .newBuilder()
            .addHeader("Authorization", authHeader)
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            .addHeader("aw-tenant-code", "fq4Bi1p7DHsSpjxgeHdvWf41YEkpZ3PCIEbClTwj7mY=")
            .build()

        return chain.proceed(request)
    }

    private fun buildBasicUEMHeader(): String {

        val userName = "td.scotcurry"
        val password = "AirWatch1"

        val stringToConvert = "$userName:$password"
        val base64String: String = Base64.getEncoder().encodeToString(stringToConvert.toByteArray())
        val authString = "Basic $base64String"
        Log.d(TAG, authString)
        return authString
    }
}