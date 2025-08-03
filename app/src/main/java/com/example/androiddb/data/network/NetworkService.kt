package com.example.androiddb.data.network

import android.util.Log
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
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody



class NetworkService : UserRepositoryInterface, HeroRepositoryInterface {
    override suspend fun performLoginRequest(user: UserLogin): UserRepositoryInterface.LoginResponse {
        Log.d("LoginRepo", "🚀 performLoginRequest() ejecutándose...")

        val client = OkHttpClient()
        val url = "${BASE_URL}auth/login"
        Log.d("LoginRepo", "🌍 URL: $url")

        val json = user.toJson()
        Log.d("LoginRepo", "📦 JSON enviado: $json")

        val credentials = Credentials.basic(user.name, user.password)
        Log.d("LoginRepo", "🔐 Credenciales Basic: $credentials")

        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", credentials)
            .post(json.toRequestBody("application/json".toMediaType()))
            .build()

        Log.d("LoginRepo", "📤 Enviando petición...")

        val response = client.newCall(request).execute()

        val responseCode = response.code
        val responseBody = response.body?.string()

        Log.d("LoginRepo", "🔁 Código HTTP: $responseCode")
        Log.d("LoginRepo", "📨 Respuesta: $responseBody")

        return if (response.isSuccessful) {
            Log.d("LoginRepo", "✅ Login exitoso. Token recibido.")
            UserRepositoryInterface.LoginResponse.Success(responseBody ?: "")
        } else {
            Log.e("LoginRepo", "❌ Login fallido. Código: $responseCode, Mensaje: ${response.message}")
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
