package com.example.androiddb.data.network

import androidx.lifecycle.viewModelScope
import com.example.androiddb.ViewModels.heros.HerosViewModel
import com.example.androiddb.data.entities.Heroes
import com.example.androiddb.data.repositories.login.UserLogin
import com.example.androiddb.data.network.Tools.BASE_URL
import com.example.androiddb.data.repositories.heroes.HeroRepositoryInterface
import com.example.androiddb.data.repositories.login.UserRepositoryInterface
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.Credentials
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody



class NetworkService : UserRepositoryInterface {
    override suspend fun performLoginRequest(user: UserLogin): UserRepositoryInterface.LoginResponse {
        val client = OkHttpClient()
        val url = "${BASE_URL}auth/login"
        val credentials = Credentials.basic(user.name, user.password)
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", credentials)
            .post("".toRequestBody())
            .build()

        val response = client.newCall(request).execute()
        return if (response.isSuccessful) {
            val token = response.body?.string() ?: ""
            UserRepositoryInterface.LoginResponse.Success(token)
        } else {
            UserRepositoryInterface.LoginResponse.Error(response.message, response.code)
        }
    }

    override suspend fun performGetHeroes(token: String): HeroRepositoryInterface.DownloadHeroesResponse {
        val client = OkHttpClient()
        val url = "${BASE_URL}heros/all"
        val credentials = "Bearer $token"

        val body = FormBody.Builder().add("name", "").build()
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", credentials)
            .post(body)
            .build()

        val response = client.newCall(request).execute()
        return if (response.isSuccessful) {
            val responseBody = response.body?.string()
            val json = Gson().fromJson(responseBody, Array<Heroes>::class.java)
            HeroRepositoryInterface.DownloadHeroesResponse.Success(
                json.toList().map { Heroes(it.id, it.photo, it.name) })
        } else {
            HeroRepositoryInterface.DownloadHeroesResponse.Error(response.message, response.code)
        }

    }
}
