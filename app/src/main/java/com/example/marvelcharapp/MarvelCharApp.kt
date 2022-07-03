package com.example.marvelcharapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MarvelCharApp: Application() {

    companion object {
        @JvmStatic
        lateinit var app: MarvelCharApp
    }

    override fun onCreate() {
        super.onCreate()

        app = this
    }
}