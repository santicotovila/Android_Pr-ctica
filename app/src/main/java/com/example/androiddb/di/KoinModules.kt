package com.example.androiddb.di


import com.example.androiddb.ViewModels.heros.HerosViewModel
import com.example.androiddb.ViewModels.login.LoginViewModel
import com.example.androiddb.data.network.NetworkService
import com.example.androiddb.data.repositories.heroes.HeroRepositoryInterface
import com.example.androiddb.data.repositories.login.UserRepositoryInterface
import com.example.androiddb.data.repositories.heroes.HeroRepositoryImpl
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val repositoryModule = module {
    single { NetworkService() }
    single<UserRepositoryInterface> { NetworkService() }
    single<HeroRepositoryInterface> { HeroRepositoryImpl(get())}
}

val viewModelModule = module {
    single { HerosViewModel(get()) }
    viewModel { LoginViewModel(get()) }
}