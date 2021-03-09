package org.curryware.androidarchitecture.workers

import com.crittercism.app.Crittercism
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroFitInstance {

    private val client: OkHttpClient = OkHttpClient.Builder().apply {
        addInterceptor(HeaderInterceptor())
    }.build()
    //private val instrumentedClient: OkHttpClient = Crittercism.getNetworkInstrumentation()
    //        .instrumentOkHttpClient(client)

    private val retrofit by lazy {

        // TODO: Need to get this moved away from hard coded ASAP.
        Retrofit.Builder()
            .baseUrl("https://cn1506.awmdm.com")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val restWorker: RestWorker by lazy {

        retrofit.create(RestWorker::class.java)
    }
}