package com.yourssohail.nasaapp.data.repository

enum class Status{
    RUNNING,
    SUCCESS,
    FAILED
}

class NetworkState(val state: Status,val msg : String){

    companion object{
        val LOADING : NetworkState
        val LOADED : NetworkState
        val ERROR : NetworkState

        init {
            LOADING = NetworkState(Status.RUNNING,"Running")
            LOADED = NetworkState(Status.SUCCESS,"Loaded")
            ERROR = NetworkState(Status.FAILED,"Something went wrong")

        }

    }



}