package me.vladsmirnov.cocktailapp.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.preferencesSetKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import me.vladsmirnov.cocktailapp.data.models.CocktailFilter
import java.io.IOException

class DataStoreManager(context: Context) {

    private val cocktailPreferences = context.createDataStore(name = "cocktail_preferences")

    companion object {
        val ACTIVE_FILTERS = preferencesSetKey<String>("active_filters")
    }

    suspend fun saveActiveFilters(filters: List<CocktailFilter>) {
        val filterSet = mutableSetOf<String>()
        filters.map { f -> filterSet.add(f.categoryName.replace(" ", "_")) }
        cocktailPreferences.edit { preferences ->
            preferences[ACTIVE_FILTERS] = filterSet
        }
    }

    val activeFiltersListFlow: Flow<Set<String>> = cocktailPreferences.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preference ->
            preference[ACTIVE_FILTERS] ?: mutableSetOf<String>()
        }
}
