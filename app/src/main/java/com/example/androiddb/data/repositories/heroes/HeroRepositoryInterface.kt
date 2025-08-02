
package com.example.androiddb.data.repositories.heroes
import com.example.androiddb.data.entities.Heroes


interface HeroRepositoryInterface {

    sealed class DownloadHeroesResponse {
        data class Success(val heroes: List<Heroes>) : DownloadHeroesResponse()
        data class Error(val message: String, val code: Int) : DownloadHeroesResponse()
    }

    suspend fun performDownloadHerosRequest(token: String): DownloadHeroesResponse
}
