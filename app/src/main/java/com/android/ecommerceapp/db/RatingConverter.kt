package com.android.ecommerceapp.db

import androidx.room.TypeConverter
import com.android.ecommerceapp.model.Rating
import com.google.gson.Gson


class RatingConverter() {

    private val gson = Gson()

    @TypeConverter
    fun fromRating(rating: Rating): String {
        return gson.toJson(rating)
    }

    @TypeConverter
    fun toRating(ratingString: String): Rating {
        return gson.fromJson(ratingString, Rating::class.java)
    }
}
