package com.yourssohail.nasaapp.data.model


import com.google.gson.annotations.SerializedName

data class Collection(
    @SerializedName("items")
    val items: List<Item>
)