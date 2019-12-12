package com.yourssohail.nasaapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yourssohail.nasaapp.data.api.search_api.SearchApiInterface
import com.yourssohail.nasaapp.data.model.SearchData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

class SearchDataSource(
    private val apiService : SearchApiInterface,
    private val compositeDisposable: CompositeDisposable
) {

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState : LiveData<NetworkState>
    get() = _networkState

    private val _searchDate = MutableLiveData<SearchData>()
    val searchData : LiveData<SearchData>
    get() = _searchDate

    fun fetchSearchResults(q:String?){
        _networkState.postValue(NetworkState.LOADING)


        try{
            compositeDisposable.add(
                apiService.getSearchResults(q)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _searchDate.postValue(it)
                            _networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            _networkState.postValue(NetworkState.ERROR)
                            Log.e("SearchDataSource",it.message)
                        }
                    )
            )
        }catch (e:Exception){
            Log.e("ExpSearchDataSource",e.message)
        }
    }

}