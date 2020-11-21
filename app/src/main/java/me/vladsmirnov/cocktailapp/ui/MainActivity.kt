package me.vladsmirnov.cocktailapp.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import me.vladsmirnov.cocktailapp.R
import me.vladsmirnov.cocktailapp.data.CocktailsViewModel
import me.vladsmirnov.cocktailapp.data.datastore.DataStoreManager
import me.vladsmirnov.cocktailapp.ui.adapters.CocktailsAdapter
import me.vladsmirnov.cocktailapp.utils.FilterActivityContract
import me.vladsmirnov.cocktailapp.utils.toast
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private var cocktailsAdapter: CocktailsAdapter = CocktailsAdapter()
    private val dataStoreManager: DataStoreManager by inject()
    private val cocktailsViewModel: CocktailsViewModel by viewModel()
    private val openFilterActivity = registerForActivityResult(FilterActivityContract())
    { result ->
        if (result != null) {
            this.lifecycleScope.launch {
                dataStoreManager.saveActiveFilters(result)
            }
            setupCocktailsList()
        } else toast("No Result")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = cocktailsAdapter
        }
        cocktailsViewModel.filtersLiveData.observe(this,
            {
                this.lifecycleScope.launch {
                    dataStoreManager.saveActiveFilters(it)
                }
            }
        )
        setupCocktailsList()
    }

    private fun setupCocktailsList() {
        this.lifecycleScope.launch {
            dataStoreManager.activeFiltersListFlow.collectLatest {
                cocktailsViewModel.cocktails(ArrayList(it)).collectLatest { items ->
                    cocktailsAdapter.submitData(items)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.filter_option -> {

                openFilterActivity.launch(100)
            }
        }

        return super.onOptionsItemSelected(item)

    }

}