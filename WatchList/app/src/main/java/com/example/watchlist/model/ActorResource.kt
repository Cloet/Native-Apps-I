package com.example.watchlist.model

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

data class ActorResource (
    @field:Json(name="data") val actors : List<Actor>
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.createTypedArrayList(Actor)) {

    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.writeTypedList(actors)
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