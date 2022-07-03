package com.example.marvelcharapp.di

import com.example.marvelcharapp.data.character.CharacterRepositoryImpl
import com.example.marvelcharapp.domain.character.repository.CharacterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindCharacterRepository(impl: CharacterRepositoryImpl): CharacterRepository
}