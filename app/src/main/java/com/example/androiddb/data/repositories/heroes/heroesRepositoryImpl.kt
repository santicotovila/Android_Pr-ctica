package com.example.androiddb.data.repositories.heroes

import com.example.androiddb.data.network.NetworkService

class HeroRepositoryImpl(private val networkData: NetworkService): HeroRepositoryInterface {
    override suspend fun performGetHeroes(token: String): HeroRepositoryInterface.DownloadHeroesResponse {
        return networkData.performGetHeroes(token)
    }

}