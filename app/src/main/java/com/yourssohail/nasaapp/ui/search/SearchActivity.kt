package com.yourssohail.nasaapp.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.yourssohail.nasaapp.R
import com.yourssohail.nasaapp.data.api.search_api.SearchApiClient
import com.yourssohail.nasaapp.data.api.search_api.SearchApiInterface
import com.yourssohail.nasaapp.data.model.Collection
import com.yourssohail.nasaapp.data.model.Data
import com.yourssohail.nasaapp.data.model.SearchData
import com.yourssohail.nasaapp.data.repository.NetworkState
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {


    private lateinit var viewModel: SearchViewModel
    private lateinit var searchRepository: SearchRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val queryString: String = intent?.getStringExtra("q").toString()
        supportActionBar?.title = "$queryString"
        val apiService: SearchApiInterface = SearchApiClient().getClient()
        var resultPageAdapter : SearchResultPageAdapter
        var collection : Collection ?= null

        Log.d("queryString", queryString)

        searchRepository = SearchRepository(apiService)


        viewModel = getViewModel(queryString)




        viewModel.searchData.observe(this, Observer {

            Log.d("observing","...")
            resultPageAdapter = SearchResultPageAdapter(this,it.collection.items)

            rv_result.layoutManager = LinearLayoutManager(this)
            rv_result.setHasFixedSize(true)
            rv_result.adapter = resultPageAdapter

            Log.d("adapter","attached")
        })




        viewModel.searchNetworkState.observe(this, Observer {
            pbSearch.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
        })


    }



    private fun getViewModel(queryString: String): SearchViewModel {

        Log.d("query", queryString)

        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SearchViewModel(searchRepository, queryString) as T
            }
        })[SearchViewModel::class.java]

    }
}
