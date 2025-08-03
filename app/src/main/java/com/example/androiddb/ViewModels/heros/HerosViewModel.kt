package com.example.androiddb.ViewModels.heros


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androiddb.MainState
import com.example.androiddb.data.repositories.heroes.HeroRepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HerosViewModel(
    private val heroRepository: HeroRepositoryInterface
) : ViewModel() {

    private val _mainState = MutableStateFlow<MainState>(MainState.Idle)
    val mainState: StateFlow<MainState> = _mainState

    fun getHeros(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _mainState.update { MainState.Loading }

            val response = heroRepository.performGetHeroes(token)

            when (response) {
                is HeroRepositoryInterface.DownloadHeroesResponse.Success -> {
                    _mainState.update {
                        MainState.HeroDownloaded(response.heroes)
                    }
                }

                is HeroRepositoryInterface.DownloadHeroesResponse.Error -> {
                    _mainState.update {
                        MainState.Error(response.message)
                    }
                }
            }
        }
    }
}
