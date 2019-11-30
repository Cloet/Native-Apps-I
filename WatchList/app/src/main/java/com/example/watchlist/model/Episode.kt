package com.example.watchlist.model

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

data class Episode(
    @field:Json(name="id") val id : String = "",
    @field:Json(name="airedSeason") val season: String = "",
    @field:Json(name="airedSeasonID") val seasonId: String = "",
    @field:Json(name="airedEpisodeNumber") val episodeNumber : String = "",
    @field:Json(name="episodeName") val name : String = "",
    @field:Json(name="firstAired") val firstAired: String? = "",
    @field:Json(name="overview") val overView: String = "",
    @field:Json(name="imdbId") val imdbId: String? = "",
    @field:Json(name="contentRating") val contentRating : String? = ""
) : Parcelable {
    constructor(parcel: Parcel): this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ){}

    companion object CREATOR : Parcelable.Creator<Episode> {
        override fun createFromParcel(parcel: Parcel): Episode {
            return Episode(parcel)
        }

        override fun newArray(size: Int): Array<Episode?> {
            return arrayOfNulls(size)
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(season)
        parcel.writeString(seasonId)
        parcel.writeString(episodeNumber)
        parcel.writeString(name)
        parcel.writeString(firstAired)
        parcel.writeString(overView)
        parcel.writeString(imdbId)
        parcel.writeString(contentRating)
    }

    override fun describeContents(): Int {
        return 0
    }

}