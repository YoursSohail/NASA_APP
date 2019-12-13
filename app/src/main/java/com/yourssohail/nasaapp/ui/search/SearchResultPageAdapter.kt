package com.yourssohail.nasaapp.ui.search

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.yourssohail.nasaapp.R
import com.yourssohail.nasaapp.data.model.Collection
import com.yourssohail.nasaapp.data.model.Data
import com.yourssohail.nasaapp.data.model.Item
import com.yourssohail.nasaapp.data.model.Link
import kotlinx.android.synthetic.main.result_item.view.*

class SearchResultPageAdapter(private val context : Context,private val items:List<Item>?) :
    RecyclerView.Adapter<SearchResultPageAdapter.SearchResultViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val view:View

        view = layoutInflater.inflate(R.layout.result_item,parent,false)
        return SearchResultViewHolder(view)
    }

    override fun getItemCount(): Int {

        Log.d("itemcount",items?.size.toString())


        return items?.size!!
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {

        Log.d("position",position.toString())
        holder.bind(items?.get(position)!!.data[0], items[position].links[0])

    }


    class SearchResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){



        fun bind(data : Data,link:Link){

            Log.d("bind title",data.title)
            Log.d("bind desc",data.description)
            Log.d("bind img",link.href)

            itemView.title_result.text = data.title
            itemView.description_result.text = data.description

            Glide.with(itemView.context)
                .load(link.href)
                .into(itemView.thumbnail_result)
        }

    }
}