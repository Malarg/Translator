package com.example.malar.translator.di

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val app: Context) {
    @Singleton
    @Provides
    fun providesApplication() : Context {
        return app
    }
}