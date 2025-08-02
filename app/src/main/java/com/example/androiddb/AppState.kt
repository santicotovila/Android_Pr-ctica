package com.example.androiddb

import com.example.androiddb.data.entities.Heroes

sealed class MainState{
    data object Idle: MainState()
    data object LoginSuccessful: MainState()
    data class HeroDownloaded(var heroes: List<Heroes>): MainState()
    data class Error(val message: String): MainState()
    data object Loading: MainState()
}