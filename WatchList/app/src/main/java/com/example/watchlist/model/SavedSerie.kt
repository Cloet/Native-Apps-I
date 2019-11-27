package com.example.watchlist.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import java.util.*


@Entity(tableName = "Savedserie_table")
data class SavedSerie(
    @PrimaryKey @ColumnInfo(name = "savedSerieId") val savedSerieId: String = UUID.randomUUID().toString(),
    @field:Json(name="seriesName") @ColumnInfo(name = "name") val name: String = "",
    @ColumnInfo(name = "overview") val overview: String = "",
    @ColumnInfo(name = "slug") val slug: String = "",
    @ColumnInfo(name = "status") val status: String = "",
    @field:Json(name="banner") @ColumnInfo(name = "banner_location") val banner_location: String = "",
    @ColumnInfo(name = "network") val network: String = "",
    @field:Json(name="dateFirstAired") @ColumnInfo(name = "firstAired") val dateFirstAired: String = ""
) : Parcelable {
    constructor(parcel: Parcel): this (
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }
    companion object CREATOR : Parcelable.Creator<SavedSerie> {
        override fun createFromParcel(parcel: Parcel): SavedSerie {
            return SavedSerie(parcel)
        }

        override fun newArray(size: Int): Array<SavedSerie?> {
            return arrayOfNulls(size)
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(savedSerieId)
        parcel.writeString(name)
        parcel.writeString(overview)
        parcel.writeString(slug)
        parcel.writeString(status)
        parcel.writeString(banner_location)
        parcel.writeString(network)
        parcel.writeString(dateFirstAired)
    }

    override fun describeContents(): Int {
        return 0
    }

}