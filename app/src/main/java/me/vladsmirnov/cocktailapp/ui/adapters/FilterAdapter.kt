package me.vladsmirnov.cocktailapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.filter_item.*
import me.vladsmirnov.cocktailapp.R
import me.vladsmirnov.cocktailapp.data.models.CocktailFilter

class FilterAdapter :
    RecyclerView.Adapter<FilterAdapter.ViewHolder>() {
    private var data: List<CocktailFilter>? = null
    var selectedFilters = arrayListOf<CocktailFilter>()

    private fun unselectFilter(cocktailFilter: CocktailFilter) {
        if (selectedFilters.contains(cocktailFilter)) {
            selectedFilters.remove(cocktailFilter)
        } else {
            selectedFilters.add(cocktailFilter)
        }
    }

    fun updateData(newData: List<CocktailFilter>) {
        data = newData
        selectedFilters = ArrayList(newData)
        this.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (data != null) {
            holder.bind(data!![position])
            holder.filterCheckbox.setOnClickListener {
                unselectFilter(data!![position])
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.filter_item, viewGroup, false)

        return ViewHolder(view)
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bind(item: CocktailFilter) {
            filterName.text = item.categoryName
        }
    }

    override fun getItemCount() = data?.size ?: 0
}
