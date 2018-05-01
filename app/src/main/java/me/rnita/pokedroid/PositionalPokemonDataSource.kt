package me.rnita.pokedroid

import android.arch.paging.PositionalDataSource
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber

class PositionalPokemonDataSource constructor(private val apiClient: ApiClient) : PositionalDataSource<PokemonsQuery.Pokemon>() {
    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<PokemonsQuery.Pokemon>) {
        apiClient.getPokemons(params.loadSize + params.startPosition)
                .subscribeBy(
                        onSuccess = {
                            callback.onResult(it.pokemons() ?: emptyList())
                        },
                        onError = {
                            Timber.d(it)
                        }
                )
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<PokemonsQuery.Pokemon>) {
        apiClient.getPokemons(params.requestedLoadSize)
                .subscribeBy(
                        onSuccess = {
                            callback.onResult(it.pokemons()
                                    ?: emptyList(), params.requestedLoadSize)
                        },
                        onError = {
                            Timber.d(it)
                        }
                )
    }

}