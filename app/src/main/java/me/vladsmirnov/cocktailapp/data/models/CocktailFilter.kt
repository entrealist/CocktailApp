package me.vladsmirnov.cocktailapp.data.models

import com.squareup.moshi.Json

data class CocktailFilter(
    @Json(name = "strCategory") val categoryName: String
)