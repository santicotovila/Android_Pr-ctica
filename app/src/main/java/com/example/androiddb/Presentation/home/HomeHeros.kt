package com.example.androiddb.Presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import androidx.recyclerview.widget.RecyclerView
import com.example.androiddb.data.entities.Heroes
import com.example.androiddb.databinding.FragmentsDetailBinding



class HomeHeros(private val onItemClicked: (Heroes) -> Unit) : RecyclerView.Adapter<HomeHeros.MainViewHolder>() {

    private var listHeros: List<Heroes> = emptyList()

    fun getData(newsHeros: List<Heroes>) {
        listHeros = newsHeros
        notifyDataSetChanged()
    }

    inner class MainViewHolder(private val binding: FragmentsDetailBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(cell: Heroes) {
            binding.txtHeroName.text = cell.name
            Glide.with(itemView.context).load(cell.photo).into(binding.imgHero)
            binding.root.setOnClickListener {
                onItemClicked(cell)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            FragmentsDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listHeros.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(listHeros[position])
    }

}