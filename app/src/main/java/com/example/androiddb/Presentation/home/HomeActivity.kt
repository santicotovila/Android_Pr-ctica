package com.example.androiddb.Presentation.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androiddb.MainState
import com.example.androiddb.Presentation.detail.DetailActivity
import com.example.androiddb.ViewModels.heros.HerosViewModel
import com.example.androiddb.databinding.HomeHeroesBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: HomeHeroesBinding
    private lateinit var adapter: HomeHeros
    private val herosViewModel: HerosViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomeHeroesBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val token = intent.getStringExtra("TOKEN") ?: ""

        adapter = HomeHeros { hero ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("Hero_PHOTO",hero.photo)
            intent.putExtra("Hero_ID",hero.id)
            startActivity(intent)
        }

        binding.rvHeroes.adapter = adapter
        binding.rvHeroes.layoutManager = LinearLayoutManager(this)

        herosViewModel.getHeros(token)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                herosViewModel.mainState.collect { state ->

                    when (state) {
                        is MainState.HeroDownloaded -> {
                            adapter.getData(state.heroes)
                        }
                        is MainState.Loading -> {
                            Log.d("HomeActivity", "Loading..")
                        }
                        is MainState.Error -> {
                            Log.e("HomeActivity", "Error: ${state.message}")
                        }
                        else -> Unit
                    }
                }
            }
        }
    }

}
