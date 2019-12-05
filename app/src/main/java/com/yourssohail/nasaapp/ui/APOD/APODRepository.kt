package com.yourssohail.nasaapp.ui.APOD

import androidx.lifecycle.LiveData
import com.yourssohail.nasaapp.data.api.NasaApiInterface
import com.yourssohail.nasaapp.data.model.APODData
import com.yourssohail.nasaapp.data.repository.APODDataSource
import com.yourssohail.nasaapp.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class APODRepository(
    private val apiService: NasaApiInterface
) {

    lateinit var apodDataSource: APODDataSource

    fun getAPOD(compositeDisposable: CompositeDisposable):LiveData<APODData>{
        apodDataSource = APODDataSource(apiService,compositeDisposable)
        apodDataSource.fetchAPOD()

        return apodDataSource.apodData
    }

    fun getAPODNetworkState():LiveData<NetworkState>{
        return apodDataSource.networkState
    }

}