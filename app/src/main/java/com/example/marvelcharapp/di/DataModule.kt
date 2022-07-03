package com.example.marvelcharapp.di

import com.example.marvelcharapp.data.base.NetworkExceptionsMapper
import com.example.marvelcharapp.data.character.network.CharacterNetworkDatasource
import com.example.marvelcharapp.data.character.network.CharacterService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun provideCharacterNetworkDatasource(retrofit: Retrofit, networkExceptionsMapper: NetworkExceptionsMapper): CharacterNetworkDatasource =
        CharacterNetworkDatasource(retrofit.create(CharacterService::class.java),networkExceptionsMapper)
}