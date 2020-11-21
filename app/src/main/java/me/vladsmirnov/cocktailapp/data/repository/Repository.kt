package me.vladsmirnov.cocktailapp.data.repository

import me.vladsmirnov.cocktailapp.data.models.NetworkResult
import me.vladsmirnov.cocktailapp.data.network.CocktailNetworkService
import me.vladsmirnov.cocktailapp.data.network.response_types.CocktailResponse

class Repository(private val service: CocktailNetworkService) {

    suspend fun getCocktailsByCategory(categoryName: String): CocktailResponse {
        return when (val result = service.getCocktailsByCategory(categoryName)) {
            is NetworkResult.Success -> result.data
            is NetworkResult.Error -> throw result.error
        }
    }
}