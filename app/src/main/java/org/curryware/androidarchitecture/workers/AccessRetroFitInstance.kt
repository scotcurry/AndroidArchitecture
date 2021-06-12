package org.curryware.androidarchitecture.workers

import com.crittercism.app.Crittercism
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AccessRetroFitInstance {

    private fun buildInstrumentedClient(): OkHttpClient {

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val clientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        clientBuilder.addInterceptor(logging)
        val client = clientBuilder.build()

        return Crittercism.getNetworkInstrumentation()
                .instrumentOkHttpClient(client)
    }

    private val retrofit by lazy {

        val instrumentedClient = buildInstrumentedClient()
        // TODO: Need to get this moved away from hard coded ASAP.
        instrumentedClient.let {
            Retrofit.Builder()
                    .baseUrl("https://aw-curryware-ex1.vidmpreview.com")
                    .client(instrumentedClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }
    }

    val restWorker: RestWorker by lazy {

        retrofit.create(RestWorker::class.java)
    }
}