package com.example.pokemonbox.app.di

import com.example.pokemonbox.data.repository.PokemonRepositoryImpl
import com.example.pokemonbox.domain.repository.PokemonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPokemonRepository(impl: PokemonRepositoryImpl): PokemonRepository
}
