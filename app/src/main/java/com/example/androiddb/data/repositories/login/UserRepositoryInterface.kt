package com.example.androiddb.data.repositories.login


interface UserRepositoryInterface {

    sealed class LoginResponse {
        data class Success(val token: String) : LoginResponse()
        data class Error(val message: String, val code: Int) : LoginResponse()
    }
    suspend fun performLoginRequest(user: UserLogin): LoginResponse


}
