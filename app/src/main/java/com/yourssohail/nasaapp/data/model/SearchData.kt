package com.yourssohail.nasaapp.data.model


import com.google.gson.annotations.SerializedName

data class SearchData(
    @SerializedName("collection")
    val collection: Collection
)