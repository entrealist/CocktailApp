package me.vladsmirnov.cocktailapp.data.repository

import androidx.paging.PagingSource
import me.vladsmirnov.cocktailapp.data.models.CocktailCategorized

class CocktailPagingSource(
    private val repository: Repository,
    private val activeFilters: ArrayList<String>,
) : PagingSource<String, CocktailCategorized>() {
    override suspend fun load(params: LoadParams<String>): LoadResult<String, CocktailCategorized> {
        return try {
            val query = activeFilters.removeFirst()
            val response = repository.getCocktailsByCategory(query).cocktails.run {
                this.map { item ->
                    CocktailCategorized(item.id, item.title, item.thumbUrl, query)
                }
            }
            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = query
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
