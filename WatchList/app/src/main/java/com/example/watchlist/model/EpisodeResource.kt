package com.example.watchlist.model

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

data class EpisodeResource (
    @field:Json(name="data") val episodes : List<Episode>
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.createTypedArrayList(Episode)) {

    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.writeTypedList(episodes)
    }

    companion  object CREATOR : Parcelable.Creator<EpisodeResource?> {
        override fun createFromParcel(parcel: Parcel): EpisodeResource {
            return EpisodeResource(parcel)
        }

        override fun newArray(p0: Int): Array<EpisodeResource?> {
            return arrayOfNulls(p0)
        }
    }


}