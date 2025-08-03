package com.example.androiddb

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import com.example.androiddb.di.repositoryModule
import com.example.androiddb.di.viewModelModule

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)

            modules(repositoryModule, viewModelModule)
        }
    }
}
