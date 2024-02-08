package com.eastx7.weather.api.openweathermap

import com.google.gson.GsonBuilder
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Response
import retrofit2.http.*
import com.eastx7.weather.utilities.Constants.OWM_HISTORY_SERVER_URL
//Remove in production
import okhttp3.logging.HttpLoggingInterceptor
import java.net.CookieManager
import java.net.CookiePolicy

interface OwmApi {

    @GET("city")
    suspend fun getHistory(
        @Query("units") units: String,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String
    ): Response<OwmHistory?>

    companion object {
        fun getRetrofitBuilder(cookieManager: CookieManager): Retrofit.Builder {
            //TODO: remove logger in production
            val logger =
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .cookieJar(JavaNetCookieJar(cookieManager))
                .build()

            return Retrofit.Builder()
                .baseUrl(OWM_HISTORY_SERVER_URL)
                .client(client)
        }
    }
}

interface GsonService : OwmApi {
    companion object {
        fun create(cookieManager: CookieManager): GsonService {
            val gson = GsonBuilder()
                .setLenient()
                .create()
            return OwmApi.getRetrofitBuilder(cookieManager)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(GsonService::class.java)
        }
    }
}