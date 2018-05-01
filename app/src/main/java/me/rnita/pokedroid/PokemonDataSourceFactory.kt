package me.rnita.pokedroid

import android.arch.paging.DataSource

class PokemonDataSourceFactory constructor(private val apiClient: ApiClient) : DataSource.Factory<Int, PokemonsQuery.Pokemon>() {
    override fun create(): DataSource<Int, PokemonsQuery.Pokemon> = PageKeyedPokemonDataSource(apiClient)
}