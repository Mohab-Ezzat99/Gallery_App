package com.example.galleryapp.di

import com.example.galleryapp.api.UnSplashApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUnSplashApi():UnSplashApi=
        Retrofit.Builder()
            .baseUrl(UnSplashApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UnSplashApi::class.java)
}