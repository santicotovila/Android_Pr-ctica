package com.example.androiddb.Presentation.detail

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.androiddb.R
import com.example.androiddb.databinding.DetailHeroBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: DetailHeroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailHeroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val photoDetail = intent.getStringExtra("Hero_PHOTO")

        photoDetail?.let {
            Glide.with(this).load(it).into(binding.imgHeroDetail)
        }

    }
}