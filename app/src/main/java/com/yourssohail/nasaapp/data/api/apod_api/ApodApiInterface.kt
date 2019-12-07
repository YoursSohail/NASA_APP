package com.yourssohail.nasaapp.data.api.apod_api

import com.yourssohail.nasaapp.data.model.APODData
import io.reactivex.Single
import retrofit2.http.GET

interface ApodApiInterface {

    @GET("planetary/apod")
    fun fetchAPOD():Single<APODData>

}