package me.vladsmirnov.cocktailapp

import android.app.Application
import me.vladsmirnov.cocktailapp.di.dataStoreModule
import me.vladsmirnov.cocktailapp.di.networkModule
import me.vladsmirnov.cocktailapp.di.repositoryModule
import me.vladsmirnov.cocktailapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class CocktailApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CocktailApp)
            modules(listOf(dataStoreModule, networkModule, repositoryModule, viewModelModule))
        }
    }
}