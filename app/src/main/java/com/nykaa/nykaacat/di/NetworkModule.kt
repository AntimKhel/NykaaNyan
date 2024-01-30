package com.nykaa.nykaacat.di

import android.util.Log
import com.nykaa.nykaacat.BuildConfig
import com.nykaa.nykaacat.network.CatApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor(
                logger = {
                    Log.d("NETWORKING_CLIENT", it)
                }
            ).apply {
                if (BuildConfig.DEBUG)
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                else
                    setLevel(HttpLoggingInterceptor.Level.NONE)
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okhHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okhHttpClient)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(
        retrofit: Retrofit
    ): CatApiService = retrofit.create(CatApiService::class.java)
}