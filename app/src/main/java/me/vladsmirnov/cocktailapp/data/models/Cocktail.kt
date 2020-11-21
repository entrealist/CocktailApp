package me.vladsmirnov.cocktailapp.data.models

import com.squareup.moshi.Json

data class Cocktail(
    @Json(name = "idDrink") val id: Int,
    @Json(name = "strDrink") val title: String,
    @Json(name = "strDrinkThumb") val thumbUrl: String,
)

data class CocktailCategorized(
    val id: Int,
    val title: String,
    val thumbUrl: String,
    var category: String
)

sealed class CocktailModel {
    data class CocktailItem(val cocktail: CocktailCategorized) : CocktailModel()
    data class CategoryItem(val title: String) : CocktailModel()
}
