package com.yourssohail.nasaapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yourssohail.nasaapp.data.api.apod_api.ApodApiInterface
import com.yourssohail.nasaapp.data.model.ApodData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

class ApodDataSource(
    private val apiService : ApodApiInterface,
    private val compositeDisposable: CompositeDisposable
) {

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState : LiveData<NetworkState>
    get() = _networkState

    private val _apodData = MutableLiveData<ApodData>()
    val apodData : LiveData<ApodData>
    get() = _apodData

    fun fetchAPOD(){
        _networkState.postValue(NetworkState.LOADING)

        try{
            compositeDisposable.add(
                apiService.fetchAPOD()
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _apodData.postValue(it)
                            _networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            _networkState.postValue(NetworkState.ERROR)
                            Log.e("ApodDataSource",it.message)
                        }
                    ))
        }catch (e:Exception){
            Log.e("ApodDataSource",e.message)
        }
    }
}