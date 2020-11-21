package me.vladsmirnov.cocktailapp.di

import me.vladsmirnov.cocktailapp.data.network.CocktailNetworkService
import me.vladsmirnov.cocktailapp.data.repository.Repository
import org.koin.dsl.module

val repositoryModule = module {
    single { createRepository(get()) }
}

fun createRepository(cocktailNetworkService: CocktailNetworkService): Repository = Repository(cocktailNetworkService)