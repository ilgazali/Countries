package com.muhammetaliilgaz.countries.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muhammetaliilgaz.countries.R
import com.muhammetaliilgaz.countries.data.model.country.Data
import kotlinx.android.synthetic.main.layout_country_item.view.*

class CountryRecyclerViewAdapter(private val context:Context, private val countriesList: ArrayList<Data>, private val listener: Listener): RecyclerView.Adapter<CountryRecyclerViewAdapter.CardViewHolder>() {





    interface Listener {
        fun onItemClickForDetail(countryModel: Data)
        fun onItemClickForFavorite(countryModel: Data)
    }

    class CardViewHolder (view : View) : RecyclerView.ViewHolder(view){

        fun bind (countryModel: Data, listener : Listener){

            itemView.countryName.text = countryModel.name
            if(countryModel.favorite){
                itemView.toggle_favorite.isChecked = countryModel.favorite
            }

            itemView.setOnClickListener{
                listener.onItemClickForDetail(countryModel)
            }
            itemView.toggle_favorite.setOnCheckedChangeListener { _, isChecked ->
                countryModel.favorite = !countryModel.favorite
                listener.onItemClickForFavorite(countryModel)
            }




        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_country_item,parent,false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {


          holder.bind(countriesList[position],listener)


    }

    override fun getItemCount(): Int {
        return countriesList.count()
    }
}

