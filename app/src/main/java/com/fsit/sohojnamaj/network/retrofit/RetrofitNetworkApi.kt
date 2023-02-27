package com.fsit.sohojnamaj.network.retrofit

import com.fsit.sohojnamaj.model.PrayerTimeResponse
import com.fsit.sohojnamaj.network.NetworkDataSource
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET
import javax.inject.Inject
import javax.inject.Singleton

private interface RetrofitNetworkApi {
    @GET(value = "/v1/timings?latitude=22.9055&longitude=89.2194&month=2&year=2023&method=1&school=1&date=26")
    suspend fun getPrayerTime(): PrayerTimeResponse

}

@Singleton
class RetrofitNetwork @Inject constructor(
    networkJson : Json
) : NetworkDataSource {

    @OptIn(ExperimentalSerializationApi::class)
    private val networkApi = Retrofit.Builder()
        .baseUrl("https://api.aladhan.com/")
        .client(
            OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor().apply {
                        setLevel(HttpLoggingInterceptor.Level.BODY)
                    }
                )
                .build()
        )
        .addConverterFactory(
            networkJson.asConverterFactory("application/json".toMediaType())
        )
        .build()
        .create(RetrofitNetworkApi::class.java)

    override suspend fun getPrayerTime():PrayerTimeResponse= networkApi.getPrayerTime()
}