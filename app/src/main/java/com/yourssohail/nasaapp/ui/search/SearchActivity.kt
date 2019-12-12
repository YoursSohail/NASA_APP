package com.yourssohail.nasaapp.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.yourssohail.nasaapp.R
import com.yourssohail.nasaapp.data.api.search_api.SearchApiClient
import com.yourssohail.nasaapp.data.api.search_api.SearchApiInterface
import com.yourssohail.nasaapp.data.model.SearchData
import com.yourssohail.nasaapp.data.repository.NetworkState
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {

    
    private lateinit var viewModel: SearchViewModel
    private lateinit var searchRepository: SearchRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

       val queryString : String? = intent.getStringExtra("q")
       val apiService : SearchApiInterface = SearchApiClient().getClient()

        searchRepository = SearchRepository(apiService)

        viewModel = getViewModel(queryString)

        viewModel.searchData.observe(this, Observer {
            bindUI(it)
        })

        viewModel.searchNetworkState.observe(this, Observer {
            pbSearch.visibility = if(it == NetworkState.LOADING) View.VISIBLE else View.GONE
        })
    }

    private fun bindUI(it: SearchData?) {

        Log.d("text",it?.collection!!.items[0].href)

        temp_txt.text = it?.collection!!.items[0].href

    }

    private fun getViewModel(queryString: String?): SearchViewModel {

        Log.d("query",queryString)

        return ViewModelProviders.of(this,object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SearchViewModel(searchRepository,queryString) as T
            }
        })[SearchViewModel::class.java]

    }
}
