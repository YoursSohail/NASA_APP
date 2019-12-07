package com.yourssohail.nasaapp.ui.APOD

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.yourssohail.nasaapp.R
import com.yourssohail.nasaapp.data.api.apod_api.NasaApiClient
import com.yourssohail.nasaapp.data.api.apod_api.ApodApiInterface
import com.yourssohail.nasaapp.data.model.APODData
import com.yourssohail.nasaapp.data.repository.NetworkState
import com.yourssohail.nasaapp.ui.Search.SearchActivity
import kotlinx.android.synthetic.main.activity_apod.*

class APODActivity : AppCompatActivity() {

    lateinit var apodViewModel: APODViewModel
    lateinit var apodRepository: APODRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apod)

        val apiService : ApodApiInterface = NasaApiClient()
            .getClient()
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

        ivAPOD.layoutParams.height = screenHeight/3

        btSearch.setOnClickListener {
            val searchText : String = etSearch.text.toString()
            if(searchText.trim().isEmpty()){
                Toast.makeText(this,"Entry your exploration first",Toast.LENGTH_SHORT).show()
            }else
            startActivity(Intent(this,SearchActivity::class.java))
        }


        tvAPODTitle.text = "Picture of the day \n["+it.title+"]"
        tvAPODExplanation.text = '"'+it.explanation+'"'+"\n- "+it.copyright


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
