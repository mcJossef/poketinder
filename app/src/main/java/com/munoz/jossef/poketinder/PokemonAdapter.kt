package com.munoz.jossef.poketinder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.munoz.jossef.poketinder.databinding.ItemPokemonBinding

class PokemonAdapter (
    var list : List<PokemonResponse>
    ): RecyclerView.Adapter<PokemonAdapter.ViewHolder>(){

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
            val binding = ItemPokemonBinding.bind(view)

            fun bind(pokemon: PokemonResponse){
                binding.tvName.text = pokemon.name
                Glide
                    .with(itemView)
                    .load(pokemon.getPokemonImage())
                    .into(binding.ivPokemon)
            }

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemPokemonBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view.root)
    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        val itemPokemon = list[position]
        holder.bind(itemPokemon)
    }
    override fun getItemCount(): Int = list.size

    }