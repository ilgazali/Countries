package com.muhammetaliilgaz.countries.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.muhammetaliilgaz.countries.R
import com.muhammetaliilgaz.countries.const.Const
import com.muhammetaliilgaz.countries.data.database.entity.CountryEntity
import com.muhammetaliilgaz.countries.data.database.instance.CountryDatabase
import com.muhammetaliilgaz.countries.data.model.country.CountryModel
import com.muhammetaliilgaz.countries.data.model.country.Data
import com.muhammetaliilgaz.countries.service.api.ApiService
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    var countryDatabase : CountryDatabase? = null
    private var countries : ArrayList<Data> ?= null
    private var countriesEntityList : List<CountryEntity>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        countryDatabase = CountryDatabase.getStudentDatabase(this)

        loadData()
    }

    private fun getView(){
        setContentView(R.layout.activity_main)
        val navController = findNavController(R.id.fragment)
       // val appBarConfiguration = AppBarConfiguration(setOf(R.id.countryFragment,R.id.favoriteFragment))
      //  setupActionBarWithNavController(navController,appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)
    }

    private fun loadData(){
        countriesEntityList =  countryDatabase?.countryDao()?.getAllCountries()
        if(countriesEntityList == null || countriesEntityList!!.isEmpty()){
            val retrofit = Retrofit.Builder()
                .baseUrl(Const.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(ApiService::class.java)
            val call = service.getCountries(Const.X_RapidAPI_Key,"10")

            call.enqueue(object : Callback<CountryModel> {
                override fun onResponse(
                    call: Call<CountryModel>,
                    response: Response<CountryModel>
                ) {
                    if (response.isSuccessful){
                        response.body()?.let {

                            //Countries List
                            countries = ArrayList(response.body()!!.data)

                            countriesEntityList = mutableListOf()
                            countries!!.forEach {

                                (countriesEntityList as MutableList<CountryEntity>).add(CountryEntity(
                                    0,
                                    it.code,
                                    it.name,
                                    it.wikiDataId,
                                    false
                                ))

                            }
                            countryDatabase!!.countryDao().insertAll(countriesEntityList!!)
                            getView()


                        }
                    }

                }

                override fun onFailure(call: Call<CountryModel>, t: Throwable) {
                    t.printStackTrace()
                    recreate();
                }

            })
        }else{
            getView()
        }
    }




}