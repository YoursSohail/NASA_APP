package com.yourssohail.nasaapp.ui.search

import androidx.lifecycle.LiveData
import com.yourssohail.nasaapp.data.api.search_api.SearchApiInterface
import com.yourssohail.nasaapp.data.model.SearchData
import com.yourssohail.nasaapp.data.repository.NetworkState
import com.yourssohail.nasaapp.data.repository.SearchDataSource
import io.reactivex.disposables.CompositeDisposable

class SearchRepository(
    private val apiService : SearchApiInterface
) {
    lateinit var searchDataSource : SearchDataSource

    fun getSearchResults(compositeDisposable: CompositeDisposable,q:String?):LiveData<SearchData>{

        searchDataSource = SearchDataSource(apiService,compositeDisposable)
        searchDataSource.fetchSearchResults(q)

        return searchDataSource.searchData

    }

    fun getNetworkState():LiveData<NetworkState>{
        return searchDataSource.networkState
    }
}