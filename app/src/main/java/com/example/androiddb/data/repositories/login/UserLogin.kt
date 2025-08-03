package com.example.androiddb.data.repositories.login

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class UserLogin(
    @SerializedName("username") val name: String = "",
    val password: String = ""
) {
    fun toJson(): String = Gson().toJson(this)
}
