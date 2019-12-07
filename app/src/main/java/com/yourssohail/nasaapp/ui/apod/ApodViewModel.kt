package com.yourssohail.nasaapp.ui.apod

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.yourssohail.nasaapp.data.model.ApodData
import com.yourssohail.nasaapp.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class ApodViewModel(
    private val apodRepository: ApodRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val apodData : LiveData<ApodData> by lazy {
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