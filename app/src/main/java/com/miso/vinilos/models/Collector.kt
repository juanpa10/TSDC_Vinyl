package com.miso.vinilos.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.*

@Entity(tableName = "collectors_table")
class Collector(
    @PrimaryKey val id: Int,
    val name: String,
    val telephone: String,
    val email: String,
    val comments: List<Comment>,
    val favoritePerformers: List<Artist>
) : Parcelable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        other as Collector
        return id == other.id &&
                name == other.name &&
                telephone == other.telephone &&
                email == other.email &&
                comments == other.comments &&
                favoritePerformers == other.favoritePerformers
    }

    override fun hashCode(): Int {
        return 31 * id.hashCode() +
                name.hashCode() +
                telephone.hashCode() +
                email.hashCode() +
                comments.hashCode() +
                favoritePerformers.hashCode()
    }

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