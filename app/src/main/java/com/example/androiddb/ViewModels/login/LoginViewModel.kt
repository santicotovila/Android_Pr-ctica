package com.example.androiddb.ViewModels.login


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androiddb.MainState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.androiddb.data.repositories.login.UserRepositoryInterface
import com.example.androiddb.data.repositories.heroes.HeroRepositoryInterface
import com.example.androiddb.data.repositories.login.UserLogin
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userRepository: UserRepositoryInterface ,
    private val heroRepository: HeroRepositoryInterface ,
): ViewModel() {

    private val _mainState = MutableStateFlow<MainState>(MainState.Idle)
    val mainState: StateFlow<MainState> = _mainState

    fun performLogin(usuario: String, pass: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _mainState.update {
                MainState.Loading
            }
            val response = userRepository.performLoginRequest(UserLogin(usuario, pass))
            when (response) {
                is UserRepositoryInterface.LoginResponse.Success -> {
                    (response.token)
                }

                is UserRepositoryInterface.LoginResponse.Error -> {
                    _mainState.update {
                        MainState.Error(response.message)
                    }
                }
            }
        }
    }

}

