package com.miso.vinilos.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.*

@Entity(tableName = "collectors_table")
class Collector (
    @PrimaryKey val id: Int,
    val name: String,
    val telephone: String,
    val email: String,
    val comments: List<Comment>,
    val favoritePerformers: List<Artist>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.createTypedArrayList(Comment.CREATOR) ?: emptyList(),
        parcel.createTypedArrayList(Artist.CREATOR) ?: emptyList()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(telephone)
        parcel.writeString(email)
        parcel.writeTypedList(comments)
        parcel.writeTypedList(favoritePerformers)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Collector> {
        override fun createFromParcel(parcel: Parcel): Collector {
            return Collector(parcel)
        }

        override fun newArray(size: Int): Array<Collector?> {
            return arrayOfNulls(size)
        }
    }
}