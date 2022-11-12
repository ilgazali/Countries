package com.muhammetaliilgaz.countries.data.database.dao

import androidx.room.*
import com.muhammetaliilgaz.countries.data.database.entity.CountryEntity

@Dao
interface CountryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
      fun insertAll(country:List<CountryEntity>)

    @Update
      fun update(country: CountryEntity)

    @Query("SELECT * FROM country")
     fun getAllCountries(): List<CountryEntity>

    @Query("SELECT * FROM country WHERE favorite = 1")
    fun getAllFavorites(): List<CountryEntity>
}