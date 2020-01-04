package com.example.watchlist.model

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

/**
 * Class used to handle data retrieved from the Api.
 * */
data class ActorResource (
    @field:Json(name="data") val actors : List<Actor>
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.createTypedArrayList(Actor)) {
    }

    override fun describeContents(): Int {
        return 0
    }

    /**
     * Writes data to [Actor] list.
     * @param parcel [Parcel]
     * @param p1 [Int]
     * */
    override fun writeToParcel(parcel: Parcel?, p1: Int) {
        parcel?.writeTypedList(actors)
    }

    companion  object CREATOR : Parcelable.Creator<ActorResource?> {
        override fun createFromParcel(parcel: Parcel): ActorResource {
            return ActorResource(parcel)
        }

        override fun newArray(p0: Int): Array<ActorResource?> {
            return arrayOfNulls(p0)
        }
    }


}