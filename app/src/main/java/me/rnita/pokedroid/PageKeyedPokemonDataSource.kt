package me.rnita.pokedroid

import android.arch.paging.PageKeyedDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber

class PageKeyedPokemonDataSource constructor(private val apiClient: ApiClient) : PageKeyedDataSource<Int, PokemonsQuery.Pokemon>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, PokemonsQuery.Pokemon>) {
        apiClient.getPokemons(params.requestedLoadSize)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {
                            callback.onResult(it.pokemons() ?: emptyList(), null, 10)
                        },
                        onError = {
                            Timber.d(it)
                        }
                )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, PokemonsQuery.Pokemon>) {

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, PokemonsQuery.Pokemon>) {
        // nop
    }
}