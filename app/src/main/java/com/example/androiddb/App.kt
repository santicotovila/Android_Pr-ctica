package com.example.androiddb

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import com.example.androiddb.data.repositories.login.UserRepositoryInterface
import com.example.androiddb.data.repositories.login.loginRepositoryImpl
import com.example.androiddb.data.repositories.heroes.HeroRepositoryImpl
import com.keepcoding.internet.repositories.HeroRepositoryInterface

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

val repositoryModule = module {
    single<UserRepositoryInterface> { loginRepositoryImpl() }
    single<HeroRepositoryInterface> { HeroRepositoryImpl() }
}

val viewModelModule = module {
    viewModel { MainViewModel(get(), get()) }
}