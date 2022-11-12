package com.muhammetaliilgaz.countries.view.details

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest

import com.muhammetaliilgaz.countries.R
import com.muhammetaliilgaz.countries.const.Const
import com.muhammetaliilgaz.countries.data.ViewModel.CountrySingletonViewModel
import com.muhammetaliilgaz.countries.data.database.entity.CountryEntity
import com.muhammetaliilgaz.countries.data.database.instance.CountryDatabase
import com.muhammetaliilgaz.countries.data.model.country.Data
import com.muhammetaliilgaz.countries.data.model.countryDetail.DetailModel
import com.muhammetaliilgaz.countries.service.api.ApiService
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class DetailsActivity : AppCompatActivity() {

    private var country : Data? = null

    var countryDatabase : CountryDatabase? = null
    private var flagImageUri : String ?= null
    private var wikiDataId : String ?= null
    private var countrySingletonViewModel : CountrySingletonViewModel = CountrySingletonViewModel.getInstance()





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        countryDatabase = CountryDatabase.getStudentDatabase(this)
        country = countrySingletonViewModel.country

        countyCode.text = country!!.code
        country_name_toolbar.text = country!!.name
        if(country!!.favorite){
            toggle_favorite.isChecked = country!!.favorite
        }

        loadData()


        button.setOnClickListener {
            wikiDataId?.let { it1 -> goLink(it1) }
        }

        back_button.setOnClickListener {
            onBackPressed()
        }

        toggle_favorite.setOnCheckedChangeListener { _, isChecked ->
            country!!.favorite = !country!!.favorite
            var countries = countryDatabase!!.countryDao().getAllCountries()
            var selectedEntity : CountryEntity? = null
            countries.forEach {
                if(it.id == country!!.id){
                    selectedEntity = it
                }
            }
            selectedEntity!!.favorite = !selectedEntity!!.favorite
            countryDatabase!!.countryDao().update(selectedEntity!!)

        }



    }





    private fun loadData() {



        val retrofit = Retrofit.Builder()
            .baseUrl(Const.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ApiService::class.java)


            service.getCountryDetails(country!!.code, Const.X_RapidAPI_Key)
                .enqueue(object : Callback<DetailModel> {
                    override fun onResponse(
                        call: Call<DetailModel>,
                        response: Response<DetailModel>
                    ) {
                        if (response.isSuccessful) {
                            response.body()?.let {

                                //Country Details

                                flagImageUri = response.body()!!.detailData.flagImageUri.trim()

                                wikiDataId = response.body()!!.detailData.wikiDataId.trim()

                                flagImageView.loadUrl(flagImageUri!!.replace("http", "https"))





                            }
                        }

                    }

                    override fun onFailure(call: Call<DetailModel>, t: Throwable) {
                        println(t.message)

                    }

                })



    }

    fun ImageView.loadUrl(url: String?) {

        val imageLoader = ImageLoader.Builder(this.context)
            .componentRegistry { add(SvgDecoder(this@loadUrl.context)) }
            .build()

        val request = ImageRequest.Builder(this.context)
            .crossfade(true)
            .crossfade(500)
            .placeholder(R.drawable.error_image)
            .error(R.drawable.error_image)
            .data(url)
            .target(this)
            .build()

        imageLoader.enqueue(request)
    }

    fun goLink (wikiDataId:String){

        val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.wikidata.org/wiki/${wikiDataId}"))
        startActivity(i)
    }



}