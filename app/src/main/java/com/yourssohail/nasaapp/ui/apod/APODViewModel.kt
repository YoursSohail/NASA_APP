package com.yourssohail.nasaapp.ui.apod

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.yourssohail.nasaapp.data.model.APODData
import com.yourssohail.nasaapp.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class APODViewModel(
    private val apodRepository: APODRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val apodData : LiveData<APODData> by lazy {
        apodRepository.getAPOD(compositeDisposable)
    }

    val apodNetworkState : LiveData<NetworkState> by lazy {
        apodRepository.getAPODNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}