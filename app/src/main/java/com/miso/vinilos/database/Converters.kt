package com.miso.vinilos.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.miso.vinilos.models.Artist
import com.miso.vinilos.models.Comment

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromCommentsList(comments: List<Comment>?): String? {
        return gson.toJson(comments)
    }

    @TypeConverter
    fun toCommentsList(commentsJson: String?): List<Comment>? {
        val type = object : TypeToken<List<Comment>>() {}.type
        return gson.fromJson(commentsJson, type)
    }

    @TypeConverter
    fun fromArtistList(artistList: List<Artist>?): String? {
        return gson.toJson(artistList)
    }

    @TypeConverter
    fun toArtistList(artistListString: String?): List<Artist>? {
        val type = object : TypeToken<List<Artist>>() {}.type
        return gson.fromJson(artistListString, type)
    }
}