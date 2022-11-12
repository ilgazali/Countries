package com.muhammetaliilgaz.countries.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "country")
data class CountryEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name="code")
    val code: String,

    @ColumnInfo(name="name")
    val name: String,

    @ColumnInfo(name="wikiDataId")
    val wikiDataId: String,

    @ColumnInfo(name="favorite")
    var favorite: Boolean
)