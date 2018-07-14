package com.example.malar.translator.di

import android.app.Application
import android.content.Context
import android.support.annotation.NonNull
import com.example.malar.translator.db.Repository
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @NonNull
    @Singleton
    @Inject
    fun provideRepository(context: Context) : Repository {
        return Repository(context)
    }
}