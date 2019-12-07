package com.yourssohail.nasaapp.data.api.search_api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://images-api.nasa.gov/"

class SearchApiClient {

    fun getClient() : SearchApiInterface{

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SearchApiInterface::class.java)

    }
}