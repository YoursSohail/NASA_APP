package com.yourssohail.nasaapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yourssohail.nasaapp.data.api.apod_api.ApodApiInterface
import com.yourssohail.nasaapp.data.model.APODData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

class APODDataSource(
    private val apiService : ApodApiInterface,
    private val compositeDisposable: CompositeDisposable
) {

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState : LiveData<NetworkState>
    get() = _networkState

    private val _apodData = MutableLiveData<APODData>()
    val apodData : LiveData<APODData>
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
                            Log.e("APODDataSource",it.message)
                        }
                    ))
        }catch (e:Exception){
            Log.e("APODDataSource",e.message)
        }
    }
}