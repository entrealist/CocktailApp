package me.vladsmirnov.cocktailapp.data.network

import me.vladsmirnov.cocktailapp.data.models.NetworkResult
import me.vladsmirnov.cocktailapp.data.network.abstracts.AbstractNetworkService
import me.vladsmirnov.cocktailapp.data.network.response_types.CocktailResponse
import me.vladsmirnov.cocktailapp.data.network.response_types.CocktailsFilterResponse

class CocktailNetworkService(private val endpoint: Endpoint) : AbstractNetworkService() {

    suspend fun getCocktailFiltersList() : NetworkResult<CocktailsFilterResponse> {
        return createCall { endpoint.getCocktailFiltersList() }
    }

    suspend fun getCocktailsByCategory(categoryName: String) : NetworkResult<CocktailResponse> {
        return createCall { endpoint.getCocktailsByCategory(categoryName) }
    }
}