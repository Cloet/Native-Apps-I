package com.example.watchlist.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import java.io.Serializable
import java.util.*


@Entity(tableName = "Savedserie_table")
data class SavedSerie(
    @PrimaryKey @ColumnInfo(name = "savedSerieId") @field:Json(name="id") val savedSerieId: String = "",
    @field:Json(name="seriesName") @ColumnInfo(name = "name") val name: String = "",
    @ColumnInfo(name = "overview") val overview: String = "",
    @ColumnInfo(name = "slug") val slug: String? = "",
    @ColumnInfo(name = "status") val status: String? = "",
    @field:Json(name="banner") @ColumnInfo(name = "banner_location") val banner_location: String? = "",
    @ColumnInfo(name = "network") val network: String? = "",
    @field:Json(name="firstAired") @ColumnInfo(name = "firstAired") val firstAired: String? = "",
    @ColumnInfo(name="rating") val rating: Float
) : Parcelable, Serializable {
    constructor(parcel: Parcel): this (
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readFloat()
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
        parcel.writeString(firstAired)
        parcel.writeFloat(rating)
    }

    override fun describeContents(): Int {
        return 0
    }

}