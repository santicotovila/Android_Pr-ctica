package com.example.androiddb.Presentation.home

import android.content.Intent
import android.os.Binder
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androiddb.MainState
import com.example.androiddb.Presentation.detail.DetailActivity

import com.example.androiddb.ViewModels.heros.HerosViewModel
import com.example.androiddb.data.entities.Heroes
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

        // 👉 Token desde LoginActivity
        val token = intent.getStringExtra("TOKEN") ?: ""
        if (token.isEmpty()) {
            Toast.makeText(this, "Token no recibido", Toast.LENGTH_SHORT).show()
            return
        }

        // 🛠 Configuramos el RecyclerView
        adapter = HomeHeros { hero ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("Hero_PHOTO",hero.photo)
            intent.putExtra("Hero_ID",hero.id)
            startActivity(intent)
        }

        binding.rvHeroes.adapter = adapter
        binding.rvHeroes.layoutManager = LinearLayoutManager(this)

        // 📡 Pedimos los datos al ViewModel
        herosViewModel.getHeros(token)

        // 👁 Observamos el estado
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                herosViewModel.mainState.collect { state ->
                    Log.d("HomeActivity", "🧠 Estado recibido: $state")

                    when (state) {
                        is MainState.HeroDownloaded -> {
                            Log.d("HomeActivity", "✅ Héroes descargados")
                            adapter.getData(state.heroes)
                        }
                        is MainState.Loading -> {
                            Log.d("HomeActivity", "⏳ Cargando...")
                        }
                        is MainState.Error -> {
                            Log.e("HomeActivity", "❌ Error: ${state.message}")
                            Toast.makeText(this@HomeActivity, state.message, Toast.LENGTH_SHORT).show()
                        }
                        else -> Unit
                    }
                }
            }
        }
    }
}
