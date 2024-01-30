package com.nykaa.nykaacat.di

import com.nykaa.nykaacat.network.CatApiService
import com.nykaa.nykaacat.network.CatsRepository
import com.nykaa.nykaacat.network.CatsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideRepository(
        apiService: CatApiService,
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher
    ): CatsRepository = CatsRepositoryImpl(apiService, coroutineDispatcher)
}