package com.example.myhome.di

import com.example.myhome.repository.MyHomeRepository
import com.example.myhome.repository.MyHomeRepositoryImpl
import com.example.myhome.repository.MyHomeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    @Singleton
    @Provides
    fun provideMyHomeRepository(
        service: MyHomeService
    ): MyHomeRepository {
        return MyHomeRepositoryImpl(service = service)
    }
}