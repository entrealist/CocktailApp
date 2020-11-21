package me.vladsmirnov.cocktailapp.data.network

import me.vladsmirnov.cocktailapp.data.network.response_types.CocktailResponse
import me.vladsmirnov.cocktailapp.data.network.response_types.CocktailsFilterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Endpoint {
    @GET("list.php?c=list")
    suspend fun getCocktailFiltersList() : Response<CocktailsFilterResponse>

    @GET("filter.php")
    suspend fun getCocktailsByCategory(
        @Query("c") category: String
    ) : Response<CocktailResponse>
}