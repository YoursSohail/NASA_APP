package com.yourssohail.nasaapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.yourssohail.nasaapp.data.model.SearchData
import com.yourssohail.nasaapp.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class SearchViewModel(
    private val searchRepository: SearchRepository,
    private val q : String
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val searchData : LiveData<SearchData> by lazy {
        searchRepository.getSearchResults(compositeDisposable,q)
    }

    val searchNetworkState : LiveData<NetworkState> by lazy{
        searchRepository.getNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}