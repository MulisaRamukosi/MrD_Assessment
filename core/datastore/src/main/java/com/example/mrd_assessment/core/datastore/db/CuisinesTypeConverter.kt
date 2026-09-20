package com.example.mrd_assessment.core.datastore.db

import androidx.room.TypeConverter

class CuisinesTypeConverter {
    @TypeConverter
    fun fromListToString(cuisines: List<String>?): String {
        return cuisines?.joinToString(separator = ",") ?: ""
    }

    @TypeConverter
    fun fromStringToList(data: String?): List<String> {
        if (data.isNullOrEmpty()) return emptyList()
        return data.split(",")
    }
}
