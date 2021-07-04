package com.example.myhome.di

import com.example.myhome.repository.MyHomeService
import com.example.network.retrofit.RetrofitFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Singleton
    @Provides
    fun bindMyHomeService(): MyHomeService {

        val retrofitFactory = RetrofitFactory()
        val client = retrofitFactory.makeClient()

        val retrofit = retrofitFactory.makeRetrofit(
            baseUrl = com.example.myhome.BuildConfig.BASE_URL,
            client = client.build(),
            converter = retrofitFactory.makeConverter()
        )

        return retrofit.create(MyHomeService::class.java)
    }

}