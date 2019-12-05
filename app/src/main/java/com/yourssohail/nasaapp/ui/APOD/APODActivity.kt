package com.yourssohail.nasaapp.ui.APOD

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.yourssohail.nasaapp.R
import com.yourssohail.nasaapp.data.api.NasaApiClient
import com.yourssohail.nasaapp.data.api.NasaApiInterface
import com.yourssohail.nasaapp.data.model.APODData
import com.yourssohail.nasaapp.data.repository.APODDataSource
import com.yourssohail.nasaapp.data.repository.NetworkState
import io.reactivex.Single
import kotlinx.android.synthetic.main.activity_apod.*

class APODActivity : AppCompatActivity() {

    lateinit var apodViewModel: APODViewModel
    lateinit var apodRepository: APODRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apod)

        val apiService : NasaApiInterface = NasaApiClient().getClient()
        apodRepository = APODRepository(apiService)

        apodViewModel = getViewModel()

        apodViewModel.apodData.observe(this, Observer {
            bindUI(it)
        })

        apodViewModel.apodNetworkState.observe(this, Observer {
            pbAPOD.visibility = if(it == NetworkState.LOADING) View.VISIBLE else View.GONE
            tvAPODError.visibility = if(it == NetworkState.ERROR) View.VISIBLE else View.GONE
        })

    }

    private fun bindUI(it: APODData) {

        val screenHeight : Int = windowManager.defaultDisplay.height

        ivAPOD.layoutParams.height = screenHeight/2




        tvAPODTitle.text = it.title
        tvAPODExplanation.text = '"'+it.explanation+'"'

        Glide.with(this)
            .load(it.url)
            .centerCrop()
            .placeholder(R.drawable.nasa_bw_logo)
            .into(ivAPOD)

    }

    private fun getViewModel() :APODViewModel{

        return ViewModelProviders.of(this,object:ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return APODViewModel(apodRepository) as T
            }
        })[APODViewModel::class.java]

    }


}
