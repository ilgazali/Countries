package com.muhammetaliilgaz.countries.view.country

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.muhammetaliilgaz.countries.R
import com.muhammetaliilgaz.countries.adapter.CountryRecyclerViewAdapter
import com.muhammetaliilgaz.countries.data.ViewModel.CountrySingletonViewModel
import com.muhammetaliilgaz.countries.data.database.entity.CountryEntity
import com.muhammetaliilgaz.countries.data.database.instance.CountryDatabase
import com.muhammetaliilgaz.countries.data.model.country.CountryModel
import com.muhammetaliilgaz.countries.data.model.country.Data
import com.muhammetaliilgaz.countries.service.api.ApiService
import com.muhammetaliilgaz.countries.view.details.DetailsActivity
import kotlinx.android.synthetic.main.fragment_country.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class CountryFragment : Fragment(), CountryRecyclerViewAdapter.Listener {


    var countryDatabase : CountryDatabase? = null
    private var countriesEntityList : List<CountryEntity>? = null
    private var countries : ArrayList<Data> ?= null
    private var countryRecyclerViewAdapter : CountryRecyclerViewAdapter? = null
    private var recyclerView : RecyclerView? = null
    private var countrySingletonViewModel : CountrySingletonViewModel= CountrySingletonViewModel.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       countryDatabase = CountryDatabase.getStudentDatabase(requireContext())



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_country, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)

        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(context)
        recyclerView!!.layoutManager = layoutManager
        fillData()




        return view
    }

    override fun onResume() {
        fillData()
        super.onResume()
    }


    private fun fillData(){
        countriesEntityList = countryDatabase!!.countryDao().getAllCountries()
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


        countryRecyclerViewAdapter = CountryRecyclerViewAdapter(requireContext(),countries!!,this@CountryFragment)
        recyclerView!!.adapter = countryRecyclerViewAdapter
        countryRecyclerViewAdapter!!.notifyDataSetChanged()
    }

    override fun onItemClickForDetail(countryModel: Data) {

        Toast.makeText(requireContext(),"code ${countryModel.code}", Toast.LENGTH_SHORT).show()
        countrySingletonViewModel.setCountry(countryModel)
        val intent = Intent(context, DetailsActivity::class.java)
        startActivity(intent)
    }

    override fun onItemClickForFavorite(countryModel: Data) {

        countryDatabase!!.countryDao().update(CountryEntity(
            countryModel.id,
            countryModel.code,
            countryModel.name,
            countryModel.wikiDataId,
            countryModel.favorite
        ))
    }

}