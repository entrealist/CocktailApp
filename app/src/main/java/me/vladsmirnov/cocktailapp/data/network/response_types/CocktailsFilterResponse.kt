package me.vladsmirnov.cocktailapp.data.network.response_types

import com.squareup.moshi.Json
import me.vladsmirnov.cocktailapp.data.models.CocktailFilter

class CocktailsFilterResponse(
    @Json(name = "drinks")
    val filters: List<CocktailFilter>
)