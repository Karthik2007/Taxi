package com.karthik.taxi.network

import com.karthik.taxi.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * created by Karthik A on 18/12/18
 *
 * Retrofit client provider for network calls
 */
class ApiClient {

    private lateinit var retrofit: Retrofit.Builder

    private fun getClient(): Retrofit.Builder {

        if (!::retrofit.isInitialized) {

            retrofit = Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
        }
        return retrofit
    }

    private fun getHttpClient() : OkHttpClient
    {
        val okHttpClientBuilder = OkHttpClient.Builder()

        //connection timeout for api call
        okHttpClientBuilder.connectTimeout(120, TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(120, TimeUnit.SECONDS)

        if(BuildConfig.DEBUG)
            okHttpClientBuilder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))

        return okHttpClientBuilder.build()
    }

    /**
     * provides taxi url client
     */
    fun provideTaxiClient(): TaxiClient
    {
        var builder : Retrofit.Builder = getClient()
        builder.client(getHttpClient())

        return builder.build().create(TaxiClient::class.java)
    }
}