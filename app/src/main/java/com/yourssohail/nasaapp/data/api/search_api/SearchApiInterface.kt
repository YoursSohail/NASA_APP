package com.yourssohail.nasaapp.data.api.search_api

import com.yourssohail.nasaapp.data.model.SearchData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface SearchApiInterface {

    @GET("search?q={q}")
    fun getSearchResults(@Path("q")q:String): Single<SearchData>
}