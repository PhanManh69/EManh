package com.emanh.emanh.model

import android.os.Parcel
import android.os.Parcelable

data class FriendsModel(
    var id: Int = 0,
    var avatar: String = "",
    var username: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this (
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString()
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(avatar)
        parcel.writeString(username)
    }
    companion object CREATOR : Parcelable.Creator<FriendsModel> {
        override fun createFromParcel(parcel: Parcel): FriendsModel {
            return FriendsModel(parcel)
        }

        override fun newArray(size: Int): Array<FriendsModel?> {
            return arrayOfNulls(size)
        }
    }

}
