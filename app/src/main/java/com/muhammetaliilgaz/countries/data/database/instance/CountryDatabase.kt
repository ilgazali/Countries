package com.muhammetaliilgaz.countries.data.database.instance

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.muhammetaliilgaz.countries.data.database.dao.CountryDao
import com.muhammetaliilgaz.countries.data.database.entity.CountryEntity

@Database(entities = [CountryEntity::class], version = 1)
abstract class CountryDatabase : RoomDatabase() {

    abstract fun countryDao(): CountryDao

    companion object {
        private var instance: CountryDatabase? = null

        fun getStudentDatabase(context: Context): CountryDatabase? {

            if (instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    CountryDatabase::class.java,
                    "country.db"
                ).allowMainThreadQueries()
                    .build()
            }
            return instance
        }
    }
}