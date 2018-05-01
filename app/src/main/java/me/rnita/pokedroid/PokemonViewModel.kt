package me.rnita.pokedroid

import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import android.arch.paging.RxPagedListBuilder
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers

class PokemonViewModel : ViewModel() {

    companion object {
        private const val LOAD_COUNT = 10
    }

    private val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(LOAD_COUNT)
            .setPageSize(LOAD_COUNT)
            .build()
    val pokemons: Flowable<PagedList<PokemonsQuery.Pokemon>>

    init {
        pokemons = RxPagedListBuilder(PokemonDataSourceFactory(ApiClient()), config)
                .setFetchScheduler(Schedulers.io())
                .buildFlowable(BackpressureStrategy.LATEST)
    }

}