package com.example.watchlist.model

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

data class LoginResource(
    @field:Json(name= "token") val token: String
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(token)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LoginResource> {
        override fun createFromParcel(parcel: Parcel): LoginResource {
            return LoginResource(parcel)
        }

        override fun newArray(p0: Int): Array<LoginResource?> {
            return arrayOfNulls(p0)
        }
    }

}