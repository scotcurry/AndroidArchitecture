package org.curryware.androidarchitecture.workers

import com.crittercism.app.Crittercism
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UEMRetroFitInstance {

    private fun buildInstrumentedClient(): OkHttpClient {

        val client: OkHttpClient = OkHttpClient.Builder().apply {
            addInterceptor(UEMHeaderInterceptor())
        }.build()

        return Crittercism.getNetworkInstrumentation()
            .instrumentOkHttpClient(client)
        // return client
    }

    private val retrofit by lazy {

        val instrumentedClient = buildInstrumentedClient()
        // TODO: Need to get this moved away from hard coded ASAP.
        instrumentedClient.let {
        Retrofit.Builder()
            .baseUrl("https://cn1506.awmdm.com")
            .client(instrumentedClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        }
    }

    val restWorker: RestWorker by lazy {

        retrofit.create(RestWorker::class.java)
    }
}