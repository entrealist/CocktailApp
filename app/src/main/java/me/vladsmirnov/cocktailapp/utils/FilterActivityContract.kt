package me.vladsmirnov.cocktailapp.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import me.vladsmirnov.cocktailapp.data.models.CocktailFilter
import me.vladsmirnov.cocktailapp.ui.FilterActivity

class FilterActivityContract : ActivityResultContract<Int, ArrayList<CocktailFilter>?>() {

    override fun createIntent(context: Context, input: Int): Intent {
        return Intent(context, FilterActivity::class.java)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): ArrayList<CocktailFilter>? {
        val data = intent?.getParcelableArrayListExtra<CocktailFilter>("appliedFilters")
        return if (resultCode == Activity.RESULT_OK && data != null) {data}
        else null
    }
}