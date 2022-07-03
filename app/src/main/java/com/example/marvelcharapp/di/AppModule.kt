package com.example.marvelcharapp.di

import android.content.Context
import com.example.marvelcharapp.MarvelCharApp.Companion.app
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppContext(): Context = app
}