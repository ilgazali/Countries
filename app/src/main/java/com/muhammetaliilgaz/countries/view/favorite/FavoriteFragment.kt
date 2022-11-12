package com.muhammetaliilgaz.countries.view.favorite

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.muhammetaliilgaz.countries.R
import com.muhammetaliilgaz.countries.adapter.FavoriteRecyclerViewAdapter
import com.muhammetaliilgaz.countries.data.database.entity.CountryEntity
import com.muhammetaliilgaz.countries.data.database.instance.CountryDatabase
import com.muhammetaliilgaz.countries.data.model.country.Data
import com.muhammetaliilgaz.countries.view.details.DetailsActivity
import kotlinx.android.synthetic.main.fragment_country.*


 class FavoriteFragment : Fragment(), FavoriteRecyclerViewAdapter.Listener {


    var countryDatabase : CountryDatabase? = null
    private var countriesEntityList : List<CountryEntity>? = null
    private var countries : ArrayList<Data> ?= null
    private var favoriteRecyclerViewAdapter : FavoriteRecyclerViewAdapter? = null
    private var recyclerView : RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        countryDatabase = CountryDatabase.getStudentDatabase(requireContext())



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_favorite, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)

        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(context)
        recyclerView!!.layoutManager = layoutManager
        fillData()




        return view
    }


    private fun fillData(){
        countriesEntityList = countryDatabase!!.countryDao().getAllFavorites()
        countries = arrayListOf()
        countriesEntityList!!.forEach {
            countries!!.add(
                Data(
                    it.id,
                    it.code,
                    it.name,
                    it.wikiDataId,
                    it.favorite
                )
            )
        }

        for (model : Data in countries!!){

            println(model.name)
            println(model.code)

        }
        favoriteRecyclerViewAdapter = FavoriteRecyclerViewAdapter(requireContext(),countries!!,this@FavoriteFragment)
        recyclerView!!.adapter = favoriteRecyclerViewAdapter
    }

    override fun onItemClickForDetail(countryModel: Data) {

        Toast.makeText(requireContext(),"code ${countryModel.code}", Toast.LENGTH_SHORT).show()

        val intent = Intent(context, DetailsActivity::class.java).also {

            it.putExtra("code",countryModel.code)
            startActivity(it)

        }
    }


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onItemClickForFavorite(countryModel: Data) {
        countries!!.removeIf { x -> x.id == countryModel.id }
        favoriteRecyclerViewAdapter!!.notifyDataSetChanged()
        countryDatabase!!.countryDao().update(CountryEntity(
            countryModel.id,
            countryModel.code,
            countryModel.name,
            countryModel.wikiDataId,
            countryModel.favorite
        ))
    }

}