package me.vladsmirnov.cocktailapp.di

import me.vladsmirnov.cocktailapp.data.CocktailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { CocktailsViewModel(get()) }
}