package me.vladsmirnov.cocktailapp.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import me.vladsmirnov.cocktailapp.data.models.CocktailFilter
import me.vladsmirnov.cocktailapp.data.models.CocktailModel
import me.vladsmirnov.cocktailapp.data.repository.CocktailPagingSource
import me.vladsmirnov.cocktailapp.data.repository.Repository

class CocktailsViewModel(
    private val repository: Repository
) : ViewModel() {
    val filtersLiveData = MutableLiveData<List<CocktailFilter>>()

    init {
        getCocktailFilters()
    }

    fun cocktails(activeFilters: ArrayList<String>): Flow<PagingData<CocktailModel>> =
        Pager(PagingConfig(20)) {
            CocktailPagingSource(repository, activeFilters = activeFilters)
        }.flow.cachedIn(viewModelScope)
            .map { pagingData -> pagingData.map { CocktailModel.CocktailItem(it) } }
            .map {

                it.insertSeparators<CocktailModel.CocktailItem, CocktailModel> { before, after ->
                    // TODO() Re-implement separators insertion: compare runs on UI side and blocks main thread
                    when {
                        before == null ->
                            CocktailModel.CategoryItem("Ordinary Drink")
                        after == null -> {
                            return@insertSeparators null
                        }
                        else -> return@insertSeparators null
                    }
                }
            }

    private fun getCocktailFilters() {
        viewModelScope.launch {
            val filters = repository.getCocktailFilters().filters
            filtersLiveData.postValue(filters)
        }
    }
}


//private val CocktailModel.CocktailItem.category: String
//    get() = this.category