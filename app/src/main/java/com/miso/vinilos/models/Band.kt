package com.miso.vinilos.models

import android.os.Parcel
import android.os.Parcelable
import java.text.SimpleDateFormat
import java.util.Locale
import androidx.room.*

@Entity(tableName = "bands_table")
data class Band(
    @PrimaryKey val id: Int,
    val name: String,
    val image: String,
    val description: String,
    val creationDate: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(image)
        parcel.writeString(description)
        parcel.writeString(creationDate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Band> {
        override fun createFromParcel(parcel: Parcel): Band {
            return Band(parcel)
        }

        override fun newArray(size: Int): Array<Band?> {
            return arrayOfNulls(size)
        }
    }

    fun getFormattedCreationDate(): String {
        return try {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val date = dateFormat.parse(creationDate)
            date?.let { formattedDate.format(it) } ?: "Fecha no válida"
        } catch (e: Exception) {
            e.printStackTrace()
            "Fecha no válida"
        }
    }
}