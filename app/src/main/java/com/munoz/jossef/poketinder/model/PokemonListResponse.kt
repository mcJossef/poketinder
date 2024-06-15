package com.munoz.jossef.poketinder.model

data class PokemonListResponse(
    val count: Int,
    val next: String,
    val results: List<PokemonResponse>
)


