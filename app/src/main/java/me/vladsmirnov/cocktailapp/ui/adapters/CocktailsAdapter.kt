package me.vladsmirnov.cocktailapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.category_item.*
import kotlinx.android.synthetic.main.cocktail_item.*
import me.vladsmirnov.cocktailapp.R
import me.vladsmirnov.cocktailapp.data.models.CocktailCategorized
import me.vladsmirnov.cocktailapp.data.models.CocktailModel

class CocktailsAdapter :
    PagingDataAdapter<CocktailModel, RecyclerView.ViewHolder>(CocktailModelComparator) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val cocktailModel: CocktailModel = getItem(position)!!

        cocktailModel.let {
            when (cocktailModel) {
                is CocktailModel.CategoryItem -> {
                    val vh = holder as CategoryViewHolder
                    vh.bind(cocktailModel.title)
                }
                is CocktailModel.CocktailItem -> {
                    val vh = holder as CocktailViewHolder
                    vh.bind(cocktailModel.cocktail)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.cocktail_item -> {
                CocktailViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.cocktail_item, parent, false)
                )
            }
            else -> {
                CategoryViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.category_item, parent, false)
                )
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is CocktailModel.CocktailItem -> R.layout.cocktail_item
            is CocktailModel.CategoryItem -> R.layout.category_item
            else -> throw UnsupportedOperationException("Unknown view")
        }
    }


    class CocktailViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(item: CocktailCategorized) {
            cocktailTitle.text = item.title
            cocktailImage.visibility = View.VISIBLE
            Glide.with(containerView.context)
                .load(item.thumbUrl.plus("/preview"))
                .apply(RequestOptions().placeholder(R.drawable.ic_cocktail_placeholder))
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .into(cocktailImage)
        }
    }

    class CategoryViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(item: String) {
            categoryTitle.text = item
        }

    }

    companion object {
        private val CocktailModelComparator = object : DiffUtil.ItemCallback<CocktailModel>() {
            override fun areItemsTheSame(oldItem: CocktailModel, newItem: CocktailModel): Boolean {
                return (oldItem is CocktailModel.CocktailItem && newItem is CocktailModel.CocktailItem && oldItem.cocktail.id == newItem.cocktail.id) || (oldItem is CocktailModel.CategoryItem && newItem is CocktailModel.CategoryItem && oldItem.title == newItem.title)
            }

            override fun areContentsTheSame(
                oldItem: CocktailModel,
                newItem: CocktailModel
            ): Boolean =
                oldItem == newItem
        }
    }

}
