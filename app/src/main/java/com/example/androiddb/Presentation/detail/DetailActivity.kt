package com.example.androiddb.Presentation.detail

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.androiddb.R
import com.example.androiddb.ViewModels.heros.HerosViewModel
import com.example.androiddb.databinding.DetailHeroBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: DetailHeroBinding
    private val heroesViewModel: HerosViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailHeroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val photoDetail = intent.getStringExtra("Hero_PHOTO")

        photoDetail?.let {
            Glide.with(this).load(it).into(binding.imgHeroDetail)
        }
        val heroID = intent.getStringExtra("Hero_ID")?:return
        var currentHero = heroesViewModel.getHeroForID(heroID) ?: return


        binding.buttonAttack.setOnClickListener {
            currentHero?.let { hero ->
                val updatedHero = hero.hitHero()
                currentHero = updatedHero
                heroesViewModel.updateHero(updatedHero)
                binding.progressLifeDetail.progress = updatedHero.life
            }
        }
        binding.buttonHeal.setOnClickListener {
            currentHero?.let { hero ->
                val updatedHero = hero.healHero()
                currentHero = updatedHero
                heroesViewModel.updateHero(updatedHero)
                binding.progressLifeDetail.progress = updatedHero.life
            }
        }




    }
}