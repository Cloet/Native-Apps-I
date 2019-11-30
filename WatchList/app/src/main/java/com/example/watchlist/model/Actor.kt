package com.example.watchlist.model

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

data class Actor(
    @field:Json(name="id") val id : String = "",
    @field:Json(name="seriesId") val seriesId: String = "",
    @field:Json(name="name") val name : String = "",
    @field:Json(name="role") val role: String = "",
    @field:Json(name="sortOrder") val sortOrder : String = "",
    @field:Json(name="image") val image: String = ""
) : Parcelable {
    constructor(parcel: Parcel): this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ){}

    companion object CREATOR : Parcelable.Creator<Actor> {
        override fun createFromParcel(parcel: Parcel): Actor {
            return Actor(parcel)
        }

        override fun newArray(size: Int): Array<Actor?> {
            return arrayOfNulls(size)
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(seriesId)
        parcel.writeString(name)
        parcel.writeString(role)
        parcel.writeString(sortOrder)
        parcel.writeString(image)
    }

    override fun describeContents(): Int {
        return 0
    }

}