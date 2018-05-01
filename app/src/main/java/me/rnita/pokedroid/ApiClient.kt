package me.rnita.pokedroid

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.cache.http.HttpCachePolicy
import com.apollographql.apollo.rx2.Rx2Apollo
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient

class ApiClient {
    private val apolloClient = ApolloClient.builder()
            .okHttpClient(OkHttpClient.Builder().build())
            .defaultHttpCachePolicy(HttpCachePolicy.CACHE_FIRST)
            .serverUrl("https://graphql-pokemon.now.sh")
            .build()

    fun getPokemons(first: Int): Single<PokemonsQuery.Data> =
            Rx2Apollo.from(apolloClient.query(PokemonsQuery.builder().first(first.toLong()).build()))
                    .subscribeOn(Schedulers.io())
                    .map { it.toData() }
                    .singleOrError()

    fun getPokemon(id: String, name: String): Single<PokemonQuery.Data> =
            Rx2Apollo.from(apolloClient.query(PokemonQuery.builder().id(id).name(name).build()))
                    .subscribeOn(Schedulers.io())
                    .map { it.toData() }
                    .singleOrError()

    fun <T> com.apollographql.apollo.api.Response<T>.toData(): T =
            if (hasErrors()) throw ResponseException(errors())
            else data() ?: throw DataNullPointerException()

    class DataNullPointerException : Exception("Data is must not be null")

    class ResponseException(errors: List<com.apollographql.apollo.api.Error>) : Exception(errors.toString())
}