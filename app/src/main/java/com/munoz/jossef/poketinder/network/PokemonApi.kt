package com.munoz.jossef.poketinder.network

import com.munoz.jossef.poketinder.model.PokemonListResponse
import retrofit2.Response
import retrofit2.http.GET

interface PokemonApi {
    @GET("/api/v2/pokemon")
    suspend fun getPokemons():Response<PokemonListResponse>
}