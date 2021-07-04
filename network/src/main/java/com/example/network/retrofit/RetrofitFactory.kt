package com.example.network.retrofit

import com.example.network.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {
    fun makeRetrofit(
        baseUrl: String,
        client: OkHttpClient,
        converter: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder().apply {
            baseUrl(baseUrl)
            client(client)
            addConverterFactory(converter)
        }.build()
    }

    fun makeClient(): OkHttpClient.Builder {
        return OkHttpClient().newBuilder().apply {
            if (BuildConfig.DEBUG) {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                addInterceptor(loggingInterceptor)
            }
        }
    }

    fun makeConverter(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }
}