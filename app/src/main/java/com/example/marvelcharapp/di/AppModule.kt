package com.example.marvelcharapp.di

import android.content.Context
import com.example.marvelcharapp.MarvelCharApp.Companion.app
import com.example.marvelcharapp.data.base.RetrofitInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppContext(): Context = app

    @Provides
    fun provideRetrofitInstance(retrofitInstance: RetrofitInstance): Retrofit = retrofitInstance.getRetrofitInstance()
}