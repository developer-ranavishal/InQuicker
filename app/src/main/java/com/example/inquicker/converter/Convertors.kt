package com.example.inquicker.converter

import androidx.room.TypeConverter
import com.example.inquicker.model.Source

class Convertors {

    @TypeConverter
     fun fromSource(source: Source?) : String? {
       return source?.name
    }

    @TypeConverter
    fun toSource(name: String?): Source? {
        return name?.let { Source(it,name) }
    }
}
