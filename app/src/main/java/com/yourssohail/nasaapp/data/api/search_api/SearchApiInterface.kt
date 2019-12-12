package com.yourssohail.nasaapp.data.api.search_api

import com.yourssohail.nasaapp.data.model.SearchData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SearchApiInterface {

    @GET("search")
    fun getSearchResults(@Query("q")q:String): Single<SearchData>
}