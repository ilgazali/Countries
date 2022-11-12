package com.muhammetaliilgaz.countries.view.splash

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.muhammetaliilgaz.countries.R
import com.muhammetaliilgaz.countries.const.Const
import com.muhammetaliilgaz.countries.data.database.entity.CountryEntity
import com.muhammetaliilgaz.countries.data.database.instance.CountryDatabase
import com.muhammetaliilgaz.countries.data.model.country.CountryModel
import com.muhammetaliilgaz.countries.data.model.country.Data
import com.muhammetaliilgaz.countries.service.api.ApiService
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_splash.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import android.content.Intent
import com.muhammetaliilgaz.countries.view.MainActivity


class SplashActivity : AppCompatActivity() {
    var countryDatabase : CountryDatabase? = null
    private var countries : ArrayList<Data> ?= null
    private var countriesEntityList : List<CountryEntity>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        goneErrorItems()

        try_again.setOnClickListener {
            loadData()
        }
        loadData()
    }

    private fun goneErrorItems(){
        errorTextView.visibility = View.GONE
        try_again.visibility = View.GONE
    }

    private fun visibleErrorItems(){
        errorTextView.visibility = View.VISIBLE
        try_again.visibility = View.VISIBLE
    }

    private fun navigateMainActivity(){
        val newIntent = Intent(this@SplashActivity, MainActivity::class.java)
        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(newIntent)
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

                            //Countries Listesi
                            countries = ArrayList(response.body()!!.data)

                            for (model : Data in countries!!){

                                println(model.name)
                                println(model.code)

                            }
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
                            navigateMainActivity()


                        }
                    }

                }

                override fun onFailure(call: Call<CountryModel>, t: Throwable) {
                    t.printStackTrace()
                    visibleErrorItems()

                }

            })
        }else{
            navigateMainActivity()
        }
    }
}