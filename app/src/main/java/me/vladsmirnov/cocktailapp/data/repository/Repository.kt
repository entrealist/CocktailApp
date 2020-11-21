package me.vladsmirnov.cocktailapp.data.repository

import me.vladsmirnov.cocktailapp.data.models.NetworkResult
import me.vladsmirnov.cocktailapp.data.network.CocktailNetworkService
import me.vladsmirnov.cocktailapp.data.network.response_types.CocktailResponse
import me.vladsmirnov.cocktailapp.data.network.response_types.CocktailsFilterResponse

class Repository(private val service: CocktailNetworkService) {

    suspend fun getCocktailFilters(): CocktailsFilterResponse {
        return when (val result = service.getCocktailFiltersList()) {
            is NetworkResult.Success -> result.data
            is NetworkResult.Error -> throw result.error
        }

    }

    suspend fun getCocktailsByCategory(categoryName: String): CocktailResponse {
        return when (val result = service.getCocktailsByCategory(categoryName)) {
            is NetworkResult.Success -> result.data
            is NetworkResult.Error -> throw result.error
        }
    }
}