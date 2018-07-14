package com.example.malar.translator.di

import android.app.Application

class App : Application() {
    companion object {
        @JvmStatic
        lateinit var appComponent : AppComponent
        fun getComponent() = appComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = buildComponent()
    }

    private fun buildComponent(): AppComponent {
        return DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .repositoryModule(RepositoryModule())
                .build()
    }
}