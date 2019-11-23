package com.example.watchlist.model

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

data class SerieResource (
    @field:Json(name="data") val series : List<SavedSerie>
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.createTypedArrayList(SavedSerie)) {

    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.writeTypedList(series)
    }

    companion  object CREATOR : Parcelable.Creator<SerieResource> {
        override fun createFromParcel(parcel: Parcel): SerieResource {
            return SerieResource(parcel)
        }

        override fun newArray(p0: Int): Array<SerieResource?> {
            return arrayOfNulls(p0)
        }
    }


}