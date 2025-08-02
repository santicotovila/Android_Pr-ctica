package com.example.androiddb.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.androiddb.data.entities.Heroes


class HomeHeros(private val onItemClicked: AdapterView.OnItemClickListener):RecyclerView.Adapter<HomeHeros.MainViewHolder>() {

    private var listHeros: List<String> = Heroes

    fun getData(nuevosElementos: List<String>) {
        listHeros = nuevosElementos
        notifyDataSetChanged()
    }

    inner class MainViewHolder(private val binding: ItemMainBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(elementoAPintar: String) {
            binding.tvMain.text = elementoAPintar
            binding.root.setOnClickListener {
                Toast.makeText(binding.root.context, elementoAPintar, Toast.LENGTH_SHORT).show()
                onItemClicked.onItemClick(elementoAPintar)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ItemMainBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(lista[position])
    }

}