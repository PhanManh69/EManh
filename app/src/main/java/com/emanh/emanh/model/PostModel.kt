package com.emanh.emanh.model

import android.os.Parcel
import android.os.Parcelable

data class PostModel(
    var id: Int = 0,
    var avatar: String = "",
    var fullName: String = "",
    var username: String = "",
    var post: String = "",
    var postPhoto: MutableList<String>
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.createStringArrayList() as ArrayList<String>
    )

    override fun describeContents(): Int {
        return  0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeString(avatar)
        dest.writeString(fullName)
        dest.writeString(username)
        dest.writeString(post)
        dest.writeStringList(postPhoto)
    }

    companion object CREATOR : Parcelable.Creator<PostModel> {
        override fun createFromParcel(parcel: Parcel): PostModel {
            return PostModel(parcel)
        }

        override fun newArray(size: Int): Array<PostModel?> {
            return arrayOfNulls(size)
        }
    }
}
