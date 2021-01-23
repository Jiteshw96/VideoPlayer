package com.example.videoplayer.utils

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataConverter {

    @TypeConverter
    fun fromComments(values:ArrayList<String>):String{
        return Gson().toJson(values).toString()
    }

    @TypeConverter
    fun toComments(value: String?): ArrayList<String> {
        val gson = Gson()
        val type = object : TypeToken<ArrayList<String>>() {

        }.type
        val str:ArrayList<String> = gson.fromJson(value, type)
        return str
    }
}