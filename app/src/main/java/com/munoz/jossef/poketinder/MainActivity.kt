package com.munoz.jossef.poketinder

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.munoz.jossef.poketinder.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private var ListPokemon: List<PokemonResponse> =emptyList()

    private val adapter by lazy{PokemonAdapter(ListPokemon)}

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvTinderPokemon
        setContentView(R.layout.activity_main)
    }
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
           .baseUrl("https://pokeapi.co")
           .addConverterFactory(GsonConverterFactory.create())
           .build()
    }

    private fun getAllPokemon(){
        CoroutineScope(Dispatchers.IO).launch {
            val request =getRetrofit().create(PokemonApi::class.java).getPokemons()
            if (request.isSuccessful)
                request.body()?.let {
                    adapter.list=it.results
                    adapter.notifyDataSetChanged()
                }
        }
    }
}