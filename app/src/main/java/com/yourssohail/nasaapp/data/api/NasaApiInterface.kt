package com.yourssohail.nasaapp.data.api

import com.yourssohail.nasaapp.data.model.APODData
import io.reactivex.Single
import retrofit2.http.GET

interface NasaApiInterface {

    @GET("planetary/apod")
    fun fetchAPOD():Single<APODData>

}