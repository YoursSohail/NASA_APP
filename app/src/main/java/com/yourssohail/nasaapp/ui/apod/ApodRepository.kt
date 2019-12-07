package com.yourssohail.nasaapp.ui.apod

import androidx.lifecycle.LiveData
import com.yourssohail.nasaapp.data.api.apod_api.ApodApiInterface
import com.yourssohail.nasaapp.data.model.ApodData
import com.yourssohail.nasaapp.data.repository.ApodDataSource
import com.yourssohail.nasaapp.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class ApodRepository(
    private val apiService: ApodApiInterface
) {

    lateinit var apodDataSource: ApodDataSource

    fun getAPOD(compositeDisposable: CompositeDisposable):LiveData<ApodData>{
        apodDataSource = ApodDataSource(apiService,compositeDisposable)
        apodDataSource.fetchAPOD()

        return apodDataSource.apodData
    }

    fun getAPODNetworkState():LiveData<NetworkState>{
        return apodDataSource.networkState
    }

}