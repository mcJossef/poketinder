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
import okhttp3.OkHttpClient

class MainActivity : AppCompatActivity() {
    private var listPokemon: List<PokemonResponse> = emptyList()
    private val adapter by lazy { PokemonAdapter(listPokemon) }
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvTinderPokemon.adapter = adapter
        getAllPokemons()
    }

    private fun getRetrofit(): Retrofit {
        val httpClient = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("NAME-HEADER", "VALUE-HEADER")
                .build()
            chain.proceed(newRequest)
        }

        val client = httpClient.build()

        return Retrofit.Builder()
            .baseUrl("https://pokeapi.co")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }



    private fun getAllPokemons() {
        CoroutineScope(Dispatchers.IO).launch {
            val request = getRetrofit().create(PokemonApi::class.java).getPokemons()
            if (request.isSuccessful) {
                request.body()?.let {
                    runOnUiThread {
                        adapter.list = it.results
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        }

    }

}