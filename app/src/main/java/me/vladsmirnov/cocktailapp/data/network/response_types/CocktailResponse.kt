package me.vladsmirnov.cocktailapp.data.network.response_types

import com.squareup.moshi.Json
import me.vladsmirnov.cocktailapp.data.models.Cocktail

data class CocktailResponse(
    @Json(name = "drinks")
    val cocktails: List<Cocktail>
)