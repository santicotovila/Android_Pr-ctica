package com.example.androiddb.data.repositories.login

import com.google.gson.Gson

data class UserLogin(val name: String = "", val password: String = "") {
    fun toJson(): String = Gson().toJson(this)
}