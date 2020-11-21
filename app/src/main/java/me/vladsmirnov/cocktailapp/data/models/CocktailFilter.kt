package me.vladsmirnov.cocktailapp.data.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CocktailFilter(
    @Json(name = "strCategory") var categoryName: String
) : Parcelable

