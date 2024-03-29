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
/**
 * Model class for [SavedSerie]
 * @constructor Creates a [SavedSerie] from a [Parcel] object
 * @property savedSerieId is the id of the [SavedSerie]
 * @property name is the name of the [SavedSerie]
 * @property overview is a summary of the [SavedSerie]
 * @property slug is a part of the url used on the website of the API.
 * @property banner_location is a part of the url referencing an image of the [SavedSerie]
 * @property status is the current status of a [SavedSerie] (Continuing, completed...)
 * @property network the network that hosts the [SavedSerie]
 * @property firstAired the original air date of the [SavedSerie]
 * @property rating the rating given to the [SavedSerie] by the User.
 * */
data class SavedSerie(
    @PrimaryKey @ColumnInfo(name = "savedSerieId") @field:Json(name="id") val savedSerieId: String = "",
    @field:Json(name="seriesName") @ColumnInfo(name = "name") val name: String = "",
    @ColumnInfo(name = "overview") val overview: String = "",
    @ColumnInfo(name = "slug") val slug: String? = "",
    @ColumnInfo(name = "status") val status: String? = "",
    @field:Json(name="banner") @ColumnInfo(name = "banner_location") val banner_location: String? = "",
    @ColumnInfo(name = "network") val network: String? = "",
    @field:Json(name="firstAired") @ColumnInfo(name = "firstAired") val firstAired: String? = "",
    @ColumnInfo(name="rating") var rating: Float
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

    /**
     * Writes an [SavedSerie] object to a [Parcel]
     * @param parcel [Parcel]
     * @param flags [Int]
     * */
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