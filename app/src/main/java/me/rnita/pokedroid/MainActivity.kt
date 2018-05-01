package me.rnita.pokedroid

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val adapter = PokemonAdapter()
    private val disposables = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = adapter

        val viewModel = ViewModelProviders.of(this).get(PokemonViewModel::class.java)
        viewModel.pokemons.subscribeBy(
                onNext = {
                    adapter.submitList(it)
                },
                onError = {
                    Timber.d(it)
                }
        ).addTo(disposables)
    }

    override fun onStop() {
        super.onStop()
        disposables.clear()
    }

}
