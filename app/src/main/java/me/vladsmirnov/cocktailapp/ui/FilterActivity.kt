package me.vladsmirnov.cocktailapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_filter.*
import me.vladsmirnov.cocktailapp.R
import me.vladsmirnov.cocktailapp.data.CocktailsViewModel
import me.vladsmirnov.cocktailapp.ui.adapters.FilterAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class FilterActivity : AppCompatActivity() {
    private val cocktailsViewModel: CocktailsViewModel by viewModel()
    private lateinit var filtersAdapter: FilterAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)
        title = getString(R.string.filter)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_backbutton)
        filtersAdapter = FilterAdapter()
        cocktailsViewModel.filtersLiveData.observe(this, {
            filtersAdapter.updateData(it)
        })
        filterRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = filtersAdapter
        }

        applyFiltersButton.setOnClickListener {
            setResult(
                RESULT_OK,
                intent.putParcelableArrayListExtra("appliedFilters", filtersAdapter.selectedFilters)
            )
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }


}