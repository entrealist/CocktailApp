package me.vladsmirnov.cocktailapp.di


import me.vladsmirnov.cocktailapp.data.datastore.DataStoreManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataStoreModule = module {
    single { DataStoreManager(androidContext()) }
}

