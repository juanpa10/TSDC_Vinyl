package com.miso.vinilos.models

import android.icu.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Band (
    val id: Int,
    val name: String,
    val image: String,
    val description: String,
    val creationDate: String
) {
    fun getFormattedCreationDate(): String {
        return try {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = dateFormat.parse(creationDate)
            val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            formattedDate.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
            "Fecha no válida"
        }
    }
}