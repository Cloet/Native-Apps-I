package com.example.watchlist.model

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

/**
 * Model class for [Episode]
 * @constructor Creates an [Episode] class from a [Parcel] object.
 * @property id the id of the [Episode]
 * @property season the season of the [Episode]
 * @property seasonId the id of the season
 * @property episodeNumber the number of the episode
 * @property name the name of the [Episode]
 * @property firstAired first air date of the [Episode]
 * @property overView small summary of the [Episode]
 * @property imdbId the id imdb has on this [Episode]
 * @property contentRating the rating of this [Episode]
 * @property fileName the filename of the [Episode]
 * */
data class Episode(
    @field:Json(name="id") val id : String = "",
    @field:Json(name="airedSeason") val season: String = "",
    @field:Json(name="airedSeasonID") val seasonId: String = "",
    @field:Json(name="airedEpisodeNumber") val episodeNumber : String = "",
    @field:Json(name="episodeName") val name : String = "",
    @field:Json(name="firstAired") val firstAired: String? = "",
    @field:Json(name="overview") val overView: String = "",
    @field:Json(name="imdbId") val imdbId: String? = "",
    @field:Json(name="contentRating") val contentRating : String? = "",
    @field:Json(name="filename") val fileName: String? = ""
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

    /**
     * Writes an [Episode] to a [Parcel] object.
     * @param parcel [Parcel]
     * @param flags [Int]
     * */
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
        parcel.writeString(fileName)
    }

    override fun describeContents(): Int {
        return 0
    }

}