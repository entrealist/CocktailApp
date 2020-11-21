package me.vladsmirnov.cocktailapp.data.models

import com.squareup.moshi.Json

data class Cocktail(
    @Json(name = "idDrink") val id: Int,
    @Json(name = "strDrink") val title: String,
    @Json(name = "strDrinkThumb") val thumbUrl: String)

sealed class CocktailModel {
    data class CocktailItem(val cocktail: Cocktail) : CocktailModel()
    data class CategoryItem(val title: String) : CocktailModel()
}
